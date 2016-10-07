package ca.ulaval.glo4002.thunderbird.boarding.exception;

public class FlightNumberNotFoundException extends RuntimeException {
    String flightNumber;

    public FlightNumberNotFoundException(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
