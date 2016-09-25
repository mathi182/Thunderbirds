package ca.ulaval.glo4002.thunderbird.reservation.exception;

public class ReservationAlreadySavedException extends RuntimeException {
    public int reservationNumber;

    public ReservationAlreadySavedException(int reservationNumber) {
        super();
        this.reservationNumber = reservationNumber;
    }
}
