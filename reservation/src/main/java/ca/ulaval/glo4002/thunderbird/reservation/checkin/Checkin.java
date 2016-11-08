package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions.CheckinNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions.CheckinNotOnTimeException;
import ca.ulaval.glo4002.thunderbird.reservation.exceptions.InvalidFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.HOURS;

@Entity
public class Checkin {

    public static final String SELF = "SELF";

    private static final String PASSENGER_HASH_FIELD = "passenger_hash";
    private static final String AGENT_ID_FIELD = "by";
    private static final int MAX_LATE_CHECKIN_IN_HOUR = 6;
    private static final int MAX_EARLY_CHECKIN_IN_HOUR = 48;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID checkinHash;

    @JsonProperty(PASSENGER_HASH_FIELD)
    private UUID passengerHash;

    @JsonProperty(AGENT_ID_FIELD)
    private String agentId;

    @JsonCreator
    public Checkin(@JsonProperty("passenger_hash") UUID passengerHash, @JsonProperty("by") String agentId) {
        if (passengerHash == null) {
            throw new InvalidFieldException(PASSENGER_HASH_FIELD);
        }
        if (Strings.isNullOrEmpty(agentId)) {
            throw new InvalidFieldException(AGENT_ID_FIELD);
        }

        this.checkinHash = UUID.randomUUID();
        this.passengerHash = passengerHash;
        this.agentId = agentId;
    }

    protected Checkin() {
        // for hibernate
    }

    public static Checkin findByCheckinHash(UUID checkinHash) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        Checkin checkin = entityManager.find(Checkin.class, checkinHash);

        if (checkin == null) {
            throw new CheckinNotFoundException(checkinHash);
        }

        return checkin;
    }

    public void save() {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(this);
    }

    @JsonIgnore
    public UUID getId() {
        return checkinHash;
    }

    @JsonIgnore
    public Passenger getPassenger() {
        return Passenger.findByPassengerHash(passengerHash);
    }

    public void completeCheckin(Instant currentDate) {
        Passenger passenger = getPassenger();
        validateReservation(passenger, currentDate);

        checkinAndSave(passenger);
    }

    private void validateReservation(Passenger passenger, Instant currentDate) {
        Reservation reservation = passenger.getReservation();
        if (reservation == null) {
            throw new ReservationNotFoundException();
        }
        if (isSelfCheckin() && !isOnTimeForSelfCheckin(currentDate, reservation.getFlightDate())) {
            throw new CheckinNotOnTimeException();
        }
    }

    private boolean isSelfCheckin() {
        return agentId.equals(SELF);
    }

    private boolean isOnTimeForSelfCheckin(Instant currentDate, Instant flightDate) {
        Instant earliestSelfCheckinDate = flightDate.minus(MAX_EARLY_CHECKIN_IN_HOUR, HOURS);
        Instant latestSelfCheckinDate = flightDate.minus(MAX_LATE_CHECKIN_IN_HOUR, HOURS);

        return !(currentDate.isBefore(earliestSelfCheckinDate) || currentDate.isAfter(latestSelfCheckinDate));
    }

    private void checkinAndSave(Passenger passenger) {
        passenger.checkin();
        passenger.save();
    }

}