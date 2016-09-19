package ca.ulaval.glo4002.thunderbird.reservation;
import ca.ulaval.glo4002.thunderbird.boarding.Passenger;

import java.util.ArrayList;

public class Reservation {

    private int reservationNumber;
    private String reservationDate;
    private String reservationConfirmation;
    private String flightNumber;
    private String flightDate;
    private ArrayList<Passenger> passengers;
    private String paymentLocation;

    public Reservation(int reservation_number,
                       String reservation_date,
                       String reservation_confirmation,
                       String flightNumber,
                       String flightDate,
                       String paymentLocation,
                       ArrayList<Passenger> passengers) {
        this.reservationNumber = reservation_number;
        this.reservationDate = reservation_date;
        this.reservationConfirmation = reservation_confirmation;
        this.paymentLocation = paymentLocation;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<>(passengers);
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

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
}
