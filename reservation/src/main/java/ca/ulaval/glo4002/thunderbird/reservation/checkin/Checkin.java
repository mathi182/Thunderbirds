package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.CheckinAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.CheckinNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.UUID;

public class Checkin {
    private static final HashMap<String, Checkin> checkinStore = new HashMap<>();
    private String checkinId;
    private String passengerHash;
    private String by;
    private static final String SELF_CHECKIN = "SELF";

    public Checkin(String passengerHash,String by) {
        this.checkinId = UUID.randomUUID().toString();
        this.passengerHash = passengerHash;
        this.by = by;
    }

    public Checkin(Checkin checkin){
        this.checkinId = checkin.checkinId;
        this.passengerHash = checkin.passengerHash;
        this.by = checkin.by;
    }

    private boolean isByValid() {
        return !(Strings.isNullOrEmpty(by));
    }

    public boolean isSelfCheckin() {
        return SELF_CHECKIN.equals(by);
    }

    private boolean isPassengerHashValid(){
        return !(Strings.isNullOrEmpty(passengerHash));
    }

    public boolean isComplete() {
       return isByValid() && isPassengerHashValid();
    }

    public String getPassengerHash() {
        return passengerHash;
    }

    public String getCheckinId() {
        return checkinId;
    }

    public synchronized void save() {
        if (checkinStore.containsKey(checkinId)) {
            throw new CheckinAlreadySavedException(checkinId);
        }
        checkinStore.put(this.checkinId, this);
    }

    public static synchronized Checkin findByCheckinId(String checkinId) {
        Checkin checkin = checkinStore.get(checkinId);
        if (checkin == null) {
            throw new CheckinNotFoundException(checkinId);
        }
        return checkin;
    }

    protected Passenger getPassenger(){
        return Passenger.findByPassengerHash(passengerHash);
    }

    public boolean passengerExist(){
        try{
            Passenger.findByPassengerHash(passengerHash);
        }catch (PassengerNotFoundException e){
            return false;
        }
        return true;
    }

    public boolean isValid(){
        return getPassenger().isValidForCheckin();
    }
}
