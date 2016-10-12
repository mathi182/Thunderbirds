package ca.ulaval.glo4002.thunderbird.reservation.exception;

/**
 * Created by Alexis on 2016-10-11.
 */
public class PassengerAlreadyCheckedInException extends RuntimeException {
    private String passengerId;

    public PassengerAlreadyCheckedInException(String passengerId) {
        this.passengerId = passengerId;
    }
}
