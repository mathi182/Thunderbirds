package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.CheckinAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerAssembly;

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

    protected PassengerAssembly getPassenger() {
        return PassengerAssembly.findByPassengerHash(passengerHash);
    }

    public boolean passengerExist() {
        try {
            PassengerAssembly.findByPassengerHash(passengerHash);
        } catch (PassengerNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean isValid() {
        return getPassenger().isValidForCheckin();
    }

    public void completePassengerCheckin() {
        PassengerAssembly passengerAssembly = getPassenger();
        passengerAssembly.checkin();
        passengerAssembly.save();
    }
}
