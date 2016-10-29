package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

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
            throw new MissingCheckinFieldException(PASSENGER_HASH_FIELD);
        }
        if (Strings.isNullOrEmpty(agentId)) {
            throw new MissingCheckinFieldException(AGENT_ID_FIELD);
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
        Instant maxEarlySelfCheckinDate = flightDate.minus(MAX_EARLY_CHECKIN_IN_HOUR, ChronoUnit.HOURS);
        Instant maxLateSelfCheckinDate = flightDate.minus(MAX_LATE_CHECKIN_IN_HOUR, ChronoUnit.HOURS);

        boolean currentDateEqualsMaxEarlyDate = currentDate.equals(maxEarlySelfCheckinDate);
        boolean currentDateEqualsMaxLateDate = currentDate.equals(maxLateSelfCheckinDate);
        boolean currentDateAfterMaxEarlyDate = currentDate.isAfter(maxEarlySelfCheckinDate);
        boolean currentDateBeforeMaxLatestDate = currentDate.isBefore(maxLateSelfCheckinDate);

        return (currentDateEqualsMaxEarlyDate || currentDateEqualsMaxLateDate) ||
                (currentDateAfterMaxEarlyDate && currentDateBeforeMaxLatestDate);
    }
}