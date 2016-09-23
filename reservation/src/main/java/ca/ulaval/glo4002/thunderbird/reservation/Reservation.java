package ca.ulaval.glo4002.thunderbird.reservation;
import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import static ca.ulaval.glo4002.thunderbird.reservation.Util.isStringNullOrEmpty;

public class Reservation {

    public int reservationNumber;
    public String reservationDate;
    public String reservationConfirmation;
    public String flightNumber;
    public String flightDate;
    public ArrayList<Passenger> passengers;
    public String paymentLocation;

    @JsonCreator
    public Reservation(@JsonProperty("reservation_number") int reservation_number,
                       @JsonProperty("reservation_date") String reservation_date,
                       @JsonProperty("reservation_confirmation") String reservation_confirmation,
                       @JsonProperty("flight_number") String flightNumber,
                       @JsonProperty("flight_date") String flightDate,
                       @JsonProperty("payment_location") String paymentLocation,
                       @JsonProperty("passengers") ArrayList<Passenger> passengers) {
        this.reservationNumber = reservation_number;
        this.reservationDate = reservation_date;
        this.reservationConfirmation = reservation_confirmation;
        this.paymentLocation = paymentLocation;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<>(passengers);
    }

    public Reservation() {

    }

    public boolean isValid() {
        return !(isStringNullOrEmpty(flightNumber)
                || isStringNullOrEmpty(flightDate)
                || reservationNumber == 0);
    }

    public static Reservation findByReservationNumber(int reservationNumber) {
        return new Reservation();
    }
}