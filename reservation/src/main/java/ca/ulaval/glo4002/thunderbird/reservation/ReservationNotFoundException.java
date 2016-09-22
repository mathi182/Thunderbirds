package ca.ulaval.glo4002.thunderbird.reservation;

public class ReservationNotFoundException extends RuntimeException {
    int reservationNumber;

    public ReservationNotFoundException(int reservationNumber) {
        super();
        this.reservationNumber = reservationNumber;
    }
}
