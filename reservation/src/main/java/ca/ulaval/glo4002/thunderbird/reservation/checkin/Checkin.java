package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.DateLong;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

public class Checkin {
    private static final HashMap<String, Checkin> checkinStore = new HashMap<>();
    private static final long MAX_LATE_CHECKIN_IN_MILLIS = 60 * 60 * 6 * 1000L;
    private static final long MAX_EARLY_CHECKIN_IN_MILLIS = 60 * 60 * 48 * 1000L;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String SELF = "SELF";
    private String checkinId;

    @JsonProperty("passenger_hash")
    private String passengerHash;

    @JsonProperty("by")
    private String agentId;

    @JsonCreator
    public Checkin(@JsonProperty("passenger_hash") String passengerHash, @JsonProperty("by") String agentId) {
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

    public boolean passengerExist() {
        try {
            getPassenger();
        }
        catch (PassengerNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean isValid() {
        boolean isValid;
        Passenger passenger = getPassenger();
        boolean passengerValidForCheckin = passenger.isValidForCheckin();
        if (isSelfCheckin()) {
            isValid = passengerValidForCheckin && isSelfCheckinOnTime();
        }
        else {
            isValid = passengerValidForCheckin;
        }
        return isValid;
    }

    private boolean isSelfCheckin () {
        return this.agentId.equals(SELF);
    }

    public void completePassengerCheckin() {
        Passenger passenger = getPassenger();
        passenger.checkin();
        passenger.save();
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
        }
        catch (ParseException e) {
            return false;
        }
    }
}
