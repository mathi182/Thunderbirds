package ca.ulaval.glo4002.thunderbird.reservation.exception;

/**
 * Created by alexandre on 2016-10-04.
 */
public class PassengerAlreadySavedException extends RuntimeException {
    public String passengerId;

    public PassengerAlreadySavedException(String passengerId) {
        this.passengerId = passengerId;
    }
}
