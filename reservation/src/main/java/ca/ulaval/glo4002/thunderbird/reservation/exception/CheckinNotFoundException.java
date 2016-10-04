package ca.ulaval.glo4002.thunderbird.reservation.exception;

/**
 * Created by alexandre on 2016-10-04.
 */
public class CheckinNotFoundException extends RuntimeException {
    public String checkinId;

    public CheckinNotFoundException(String checkinId) {
        super();
        this.checkinId = checkinId;
    }
}
