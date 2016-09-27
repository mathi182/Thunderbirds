package ca.ulaval.glo4002.thunderbird.reservation.exception;

public class PassengerNotFoundException extends RuntimeException {
    public String passengerHash;

    public PassengerNotFoundException(String passengerHash) {
        super();
        this.passengerHash = passengerHash;
    }
}
