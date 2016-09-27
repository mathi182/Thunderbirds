package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static ca.ulaval.glo4002.thunderbird.reservation.Util.isStringNullOrEmpty;

public class Reservation {

    private static final HashMap<Integer, Reservation> reservationStore = new HashMap<>();
    @JsonProperty("reservation_number") public int reservationNumber;
    @JsonProperty("flight_number") public String flightNumber;
    @JsonProperty("flight_date") public String flightDate;
    @JsonProperty("passengers") public ArrayList<Passenger> passengers;
    @JsonIgnore public String reservationDate;
    @JsonIgnore public String reservationConfirmation;
    @JsonIgnore public String paymentLocation;

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
        this.passengers.forEach(passenger -> passenger.reservationNumber = reservationNumber);
    }

    public Reservation(Reservation reservationToCopy) {
        this.reservationNumber = reservationToCopy.reservationNumber;
        this.reservationDate = reservationToCopy.reservationDate;
        this.reservationConfirmation = reservationToCopy.reservationConfirmation;
        this.paymentLocation = reservationToCopy.paymentLocation;
        this.flightDate = reservationToCopy.flightDate;
        this.flightNumber = reservationToCopy.flightNumber;
        this.passengers = new ArrayList<>(reservationToCopy.passengers.size());
        this.passengers.addAll(reservationToCopy.passengers.stream().map(Passenger::new).collect(Collectors.toList()));
        this.passengers.forEach(passenger -> passenger.reservationNumber = reservationNumber);
    }

    public static Reservation findByReservationNumber(int reservationNumber) {
        Reservation reservation = reservationStore.get(reservationNumber);
        if (reservation == null) {
            throw new ReservationNotFoundException(reservationNumber);
        }
        return new Reservation(reservation);
    }

    public static void resetReservationStore() {
        reservationStore.clear();
    }

    @JsonIgnore
    public boolean isValid() {
        return !(isStringNullOrEmpty(flightNumber)
                || isStringNullOrEmpty(flightDate)
                || reservationNumber <= 0);
    }

    public void save() {
        if (reservationStore.containsKey(reservationNumber)) {
            throw new ReservationAlreadySavedException(reservationNumber);
        }
        reservationStore.put(reservationNumber, new Reservation(this));
    }
}
