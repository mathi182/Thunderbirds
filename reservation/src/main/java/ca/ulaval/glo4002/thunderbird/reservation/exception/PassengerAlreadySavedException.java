package ca.ulaval.glo4002.thunderbird.reservation.exception;

public class PassengerAlreadySavedException extends RuntimeException {
    public String passengerId;

    public PassengerAlreadySavedException(String passengerId) {
        this.passengerId = passengerId;
    }
}
