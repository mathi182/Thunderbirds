package ca.ulaval.glo4002.thunderbird.reservation;

public class Reservation {

    private int reservationNumber;
    private String reservationDate;
    private String reservationConfirmation;
    private String paymentLocation;

    public Reservation(int reservation_number,
                       String reservation_date,
                       String reservation_confirmation,
                       String paymentLocation) {
        this.reservationNumber = reservation_number;
        this.reservationDate = reservation_date;
        this.reservationConfirmation = reservation_confirmation;
        this.paymentLocation = paymentLocation;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getReservationConfirmation() {
        return reservationConfirmation;
    }

    public String getPaymentLocation() {
        return paymentLocation;
    }
}
