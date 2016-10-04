package ca.ulaval.glo4002.thunderbird.reservation.exception;

/**
 * Created by alexandre on 2016-10-04.
 */
public class CheckinAlreadySavedException extends RuntimeException{
    public String checkinId;

    public CheckinAlreadySavedException(String checkinId) {
        super();
        this.checkinId = checkinId;
    }
}
