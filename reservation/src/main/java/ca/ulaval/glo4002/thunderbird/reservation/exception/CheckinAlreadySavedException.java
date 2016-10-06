package ca.ulaval.glo4002.thunderbird.reservation.exception;

public class CheckinAlreadySavedException extends RuntimeException{
    public String checkinId;

    public CheckinAlreadySavedException(String checkinId) {
        this.checkinId = checkinId;
    }
}
