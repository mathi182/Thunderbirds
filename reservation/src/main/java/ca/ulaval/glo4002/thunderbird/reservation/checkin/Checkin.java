package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions.CheckinNotOnTimeException;
import ca.ulaval.glo4002.thunderbird.reservation.exceptions.MissingFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.HOURS;

public class Checkin {
    private static final String PASSENGER_HASH_FIELD = "passenger_hash";
    private static final String AGENT_ID_FIELD = "by";
    private static final HashMap<String, Checkin> checkinStore = new HashMap<>();
    private static final int MAX_LATE_CHECKIN_IN_HOUR = 6;
    private static final int MAX_EARLY_CHECKIN_IN_HOUR = 48;
    private static final String SELF = "SELF";
    private String checkinId;

    @JsonProperty(PASSENGER_HASH_FIELD)
    private String passengerHash;

    @JsonProperty(AGENT_ID_FIELD)
    private String agentId;

    @JsonCreator
    public Checkin(@JsonProperty("passenger_hash") String passengerHash, @JsonProperty("by") String agentId) {
        if (Strings.isNullOrEmpty(passengerHash)) {
            throw new MissingFieldException(PASSENGER_HASH_FIELD);
        }
        if (Strings.isNullOrEmpty(agentId)) {
            throw new MissingFieldException(AGENT_ID_FIELD);
        }

        this.checkinId = UUID.randomUUID().toString();
        this.passengerHash = passengerHash;
        this.agentId = agentId;
    }

    public String getCheckinId() {
        return checkinId;
    }

    public void save() {
        checkinStore.put(this.checkinId, this);
    }

    protected Passenger getPassenger() {
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