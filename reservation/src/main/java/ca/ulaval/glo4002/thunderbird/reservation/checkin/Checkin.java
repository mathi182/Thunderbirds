package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions.CheckinNotOnTimeException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.HOURS;

@Entity
public class Checkin {
    public static final String SELF = "SELF";
    public static final int MAX_LATE_CHECKIN_IN_HOUR = 6;
    public static final int MAX_EARLY_CHECKIN_IN_HOUR = 48;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private final UUID checkinHash = UUID.randomUUID();
    @NotNull
    private UUID passengerHash;
    @NotBlank
    private String by;

    @JsonCreator
    public Checkin(UUID passengerHash, String by) {
        this.passengerHash = passengerHash;
        this.by = by;
    }

    protected Checkin() {
        // for hibernate
    }

    public void save() {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(this);
    }

    public UUID getId() {
        return checkinHash;
    }

    //TODO Fix tests to not override this public method. GetPassenger should be Private
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
        return by.equals(SELF);
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