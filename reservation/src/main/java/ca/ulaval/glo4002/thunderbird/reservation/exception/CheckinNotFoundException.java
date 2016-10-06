package ca.ulaval.glo4002.thunderbird.reservation.exception;

public class CheckinNotFoundException extends RuntimeException {
    public String checkinId;

    public CheckinNotFoundException(String checkinId) {
        this.checkinId = checkinId;
    }
}
