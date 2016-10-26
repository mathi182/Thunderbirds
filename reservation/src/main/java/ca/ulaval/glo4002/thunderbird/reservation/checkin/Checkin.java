package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.DateLong;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

public class Checkin {
    private static final String PASSENGER_HASH_FIELD = "passenger_hash";
    private static final String AGENT_ID_FIELD = "by";
    private static final HashMap<String, Checkin> checkinStore = new HashMap<>();
    private static final long MAX_LATE_CHECKIN_IN_MILLIS = 60 * 60 * 6 * 1000L;
    private static final long MAX_EARLY_CHECKIN_IN_MILLIS = 60 * 60 * 48 * 1000L;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
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

    public synchronized void save() {
        checkinStore.put(this.checkinId, this);
    }

    protected Passenger getPassenger() {
        return Passenger.findByPassengerHash(passengerHash);
    }

    public void completePassengerCheckin() {
        if (isSelfCheckin() && !isSelfCheckinOnTime()) {
            throw new CheckinNotOnTimeException();
        }

        Passenger passenger = getPassenger();
        passenger.checkin();
        passenger.save();
    }

    private boolean isSelfCheckin() {
        return agentId.equals(SELF);
    }

    private boolean isSelfCheckinOnTime() {
        Reservation reservation = Reservation.findByReservationNumber(getPassenger().getReservationNumber());
        String flightDate = reservation.getFlightDate();
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            long parsedFlightDate = format.parse(flightDate.replaceAll("Z$", "+0000")).getTime();
            long maxEarlySelfCheckinDate = parsedFlightDate - MAX_EARLY_CHECKIN_IN_MILLIS;
            long maxLateSelfCheckinDate = parsedFlightDate - MAX_LATE_CHECKIN_IN_MILLIS;
            long currentTime = DateLong.getLongCurrentDate();
            return (currentTime > maxEarlySelfCheckinDate) && (currentTime < maxLateSelfCheckinDate);
        } catch (ParseException e) {
            return false;
        }
    }
}
