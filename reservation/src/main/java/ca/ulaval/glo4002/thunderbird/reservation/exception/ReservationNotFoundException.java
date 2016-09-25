package ca.ulaval.glo4002.thunderbird.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {
    public int reservationNumber;

    public ReservationNotFoundException(int reservationNumber) {
        super();
        this.reservationNumber = reservationNumber;
    }
}
