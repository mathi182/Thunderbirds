package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

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

    public static synchronized Reservation findByReservationNumber(int reservationNumber) {
        Reservation reservation = reservationStore.get(reservationNumber);
        if (reservation == null) {
            throw new ReservationNotFoundException(reservationNumber);
        }
        return reservation;
    }

    @JsonIgnore
    public boolean isValid() {
        return !(Strings.isNullOrEmpty(flightNumber)
                || Strings.isNullOrEmpty(flightDate)
                || reservationNumber <= 0);
    }

    public synchronized void save() {
        if (reservationStore.containsKey(reservationNumber)) {
            throw new ReservationAlreadySavedException(reservationNumber);
        }
        reservationStore.put(reservationNumber, this);
        passengers.forEach(Passenger::save);
    }

    public int getReservationNumber() {
        return reservationNumber;
    }
}