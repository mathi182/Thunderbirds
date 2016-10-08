package ca.ulaval.glo4002.thunderbird.boarding.exception;

public class SeatNotAvailableException extends RuntimeException {
    public String flightNumber;
    public String flightDate;
    public String seat;

    public SeatNotAvailableException(String flightNumber, String flightDate, String seat) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.seat = seat;
    }
}
