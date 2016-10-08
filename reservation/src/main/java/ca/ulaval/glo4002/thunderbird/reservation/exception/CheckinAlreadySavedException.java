package ca.ulaval.glo4002.thunderbird.reservation.exception;

public class CheckinAlreadySavedException extends RuntimeException{
    private String checkinId;

    public CheckinAlreadySavedException(String checkinId) {
        this.checkinId = checkinId;
    }
}
