package ca.ulaval.glo4002.thunderbird.reservation;

/**
 * Created by alexandre on 2016-09-17.
 */
public class Checkin {
    private String passenger_hash;
    private String by;
    private String checkin_id;

    public Checkin() {
    }

    public Checkin(String passengerHash, String by) {
        this.passenger_hash = passengerHash;
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getCheckin_id() {
        return checkin_id;
    }

    public void setCheckin_id(String checkin_id) {
        this.checkin_id = checkin_id;
    }

    public String getPassenger_hash() {
        return passenger_hash;
    }

    public void setPassenger_hash(String passengerHash) {
        this.passenger_hash = passengerHash;
    }
}
