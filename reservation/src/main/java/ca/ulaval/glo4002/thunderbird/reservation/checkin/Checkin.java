package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions.CheckinNotOnTimeException;
import ca.ulaval.glo4002.thunderbird.reservation.exceptions.InvalidFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
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

    private static final String PASSENGER_HASH_FIELD = "passenger_hash";
    private static final String AGENT_ID_FIELD = "by";
    private static final int MAX_LATE_CHECKIN_IN_HOUR = 6;
    private static final int MAX_EARLY_CHECKIN_IN_HOUR = 48;
    private static final String SELF = "SELF";

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

    public void save() {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        entityManagerProvider.executeInTransaction(() -> entityManager.persist(this));
    }

    @JsonIgnore
    public UUID getId() {
        return checkinHash;
    }

    private Passenger getPassenger() {
        return Passenger.findByPassengerHash(passengerHash);
    }

    public void completePassengerCheckin(Instant currentDate) {
        if (isSelfCheckin() && !isSelfCheckinOnTime(currentDate)) {
            throw new CheckinNotOnTimeException();
        }

        Passenger passenger = getPassenger();
        passenger.checkin();
        passenger.save();
    }

    private boolean isSelfCheckin() {
        return agentId.equals(SELF);
    }

    private boolean isSelfCheckinOnTime(Instant currentDate) {
        int reservationNumber = getPassenger().getReservationNumber();
        Reservation reservation = Reservation.findByReservationNumber(reservationNumber);

        Instant flightDate = reservation.getFlightDate();
        Instant earliestSelfCheckinDate = flightDate.minus(MAX_EARLY_CHECKIN_IN_HOUR, HOURS);
        Instant latestSelfCheckinDate = flightDate.minus(MAX_LATE_CHECKIN_IN_HOUR, HOURS);

        return (currentDate.equals(earliestSelfCheckinDate) || currentDate.equals(latestSelfCheckinDate)) ||
                (currentDate.isAfter(earliestSelfCheckinDate) && currentDate.isBefore(latestSelfCheckinDate));
    }

}