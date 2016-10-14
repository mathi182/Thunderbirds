package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;

import java.util.HashMap;
import java.util.UUID;

public class Checkin {
    private static final HashMap<String, Checkin> checkinStore = new HashMap<>();
    private String checkinId;
    private String passengerHash;
    private String by;
    private static final String SELF_CHECKIN = "SELF";

    Checkin(String passengerHash, String by) {
        this.checkinId = UUID.randomUUID().toString();
        this.passengerHash = passengerHash;
        this.by = by;
    }

    public boolean isSelfCheckin() {
        return SELF_CHECKIN.equals(by);
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
        } catch (PassengerNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean isValid() {
        return getPassenger().isValidForCheckin();
    }

    public void completePassengerCheckin() {
        Passenger passenger = getPassenger();
        passenger.checkin();
        passenger.save();
    }
}
