package ca.ulaval.glo4002.thunderbird.reservation;

public class ReservationAlreadySavedException extends RuntimeException {
    int reservationNumber;

    public ReservationAlreadySavedException(int reservationNumber) {
        super();
        this.reservationNumber = reservationNumber;
    }
}
