package ca.ulaval.glo4002.thunderbird.reservation;

public class Reservation {

    public int reservationNumber;
    public String reservationDate;
    public String reservationConfirmation;

    public Reservation(int reservation_number, String reservation_date, String reservation_confirmation) {
        this.reservationNumber = reservation_number;
        this.reservationDate = reservation_date;
        this.reservationConfirmation = reservation_confirmation;
    }
}
