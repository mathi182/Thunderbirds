package ca.ulaval.glo4002.thunderbird.reservation.rest.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.rest.passenger.Passenger;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class Reservation {
    private static final HashMap<Integer, Reservation> reservationStore = new HashMap<>();

    @JsonProperty("reservation_number")
    private int reservationNumber;
    @JsonProperty("flight_number")
    private String flightNumber;
    @JsonProperty("flight_date")
    private String flightDate;
    @JsonProperty("passengers")
    private ArrayList<Passenger> passengers;
    @JsonIgnore
    private String reservationDate;
    @JsonIgnore
    private String reservationConfirmation;
    @JsonIgnore
    private String paymentLocation;

    @JsonCreator
    public Reservation(@JsonProperty("reservation_number") int reservationNumber,
                       @JsonProperty("reservation_date") String reservationDate,
                       @JsonProperty("reservation_confirmation") String reservationConfirmation,
                       @JsonProperty("flight_number") String flightNumber,
                       @JsonProperty("flight_date") String flightDate,
                       @JsonProperty("payment_location") String paymentLocation,
                       @JsonProperty("passengers") ArrayList<Passenger> passengers) {
        this.reservationNumber = reservationNumber;
        this.reservationDate = reservationDate;
        this.reservationConfirmation = reservationConfirmation;
        this.paymentLocation = paymentLocation;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<>(passengers);
        this.passengers.forEach(passenger -> passenger.setReservationNumber(reservationNumber));
    }

    public static void resetReservationStore() {
        reservationStore.clear();
    }

    public static Reservation findByReservationNumber(int reservationNumber) {
        Reservation reservation = reservationStore.get(reservationNumber);
        if (reservation == null) {
            throw new ReservationNotFoundException(reservationNumber);
        }
        return reservation;
    }

    public static boolean reservationExists(int reservationNumber) {
        Reservation reservation = reservationStore.get(reservationNumber);
        return reservation != null;
    }

    public void save() {
        reservationStore.put(reservationNumber, this);
        passengers.forEach(Passenger::save);
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }
}
