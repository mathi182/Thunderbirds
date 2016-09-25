package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static ca.ulaval.glo4002.thunderbird.reservation.Util.isStringNullOrEmpty;

public class Reservation {

    private static final HashMap<Integer, Reservation> reservationStore = new HashMap<>();
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
        this.passengers.forEach(passenger -> {
            passenger.reservationNumber = this.reservationNumber;
        });
    }

    public Reservation(Reservation other) {
        this.reservationNumber = other.reservationNumber;
        this.reservationDate = other.reservationDate;
        this.reservationConfirmation = other.reservationConfirmation;
        this.paymentLocation = other.paymentLocation;
        this.flightDate = other.flightDate;
        this.flightNumber = other.flightNumber;
        this.passengers = new ArrayList<>(other.passengers.size());
        this.passengers.addAll(other.passengers.stream().map(Passenger::new).collect(Collectors.toList()));
    }

    public boolean isValid() {
        return !(isStringNullOrEmpty(flightNumber)
                || isStringNullOrEmpty(flightDate)
                || reservationNumber == 0);
    }

    public static Reservation findByReservationNumber(int reservationNumber) {
        Reservation reservation = reservationStore.get(reservationNumber);
        if (reservation == null) {
            throw new ReservationNotFoundException(reservationNumber);
        }
        return new Reservation(reservation);
    }

    public void save() {
        if (reservationStore.containsKey(this.reservationNumber)) {
            throw new ReservationAlreadySavedException(this.reservationNumber);
        }
        reservationStore.put(this.reservationNumber, new Reservation(this));
    }
}
