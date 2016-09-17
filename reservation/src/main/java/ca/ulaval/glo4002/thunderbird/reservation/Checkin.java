package ca.ulaval.glo4002.thunderbird.reservation;

/**
 * Created by alexandre on 2016-09-17.
 */
public class Checkin {
    private String passengerHash;
    private String by;
    private String checkinId;

    public Checkin() {
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(String checkinId) {
        this.checkinId = checkinId;
    }

    public String getPassengerHash() {
        return passengerHash;
    }

    public void setPassengerHash(String passengerHash) {
        this.passengerHash = passengerHash;
    }

    public Checkin(String passengerHash, String by) {
        this.passengerHash = passengerHash;
        this.by = by;
    }
}
