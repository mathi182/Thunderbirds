package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Reservation {

    private static final HashMap<Integer, Reservation> reservationStore = new HashMap<>();
    private int reservationNumber;
    private String reservationDate;
    private String reservationConfirmation;
    private String flightNumber;
    private String flightDate;
    private ArrayList<Passenger> passengers;
    private String paymentLocation;

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

    public Reservation(Reservation other) {
        this.reservationNumber = other.getReservationNumber();
        this.reservationDate = other.getReservationDate();
        this.reservationConfirmation = other.getReservationConfirmation();
        this.paymentLocation = other.getPaymentLocation();
        this.flightDate = other.getFlightDate();
        this.flightNumber = other.getFlightNumber();
        this.passengers = new ArrayList<>(other.getPassengers().size());
        this.passengers.addAll(other.passengers.stream().map(Passenger::new).collect(Collectors.toList()));
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

    public void save() {
        if (reservationStore.containsKey(this.reservationNumber)) {
            throw new ReservationAlreadySavedException(this.reservationNumber);
        }
        reservationStore.put(this.reservationNumber, new Reservation(this));
        for (Passenger passenger : passengers) {
            passenger.save();
        }
    }

    public static Reservation find(int reservationNumber) {
        Reservation reservation = reservationStore.get(reservationNumber);
        if (reservation == null) {
            throw new ReservationNotFoundException(reservationNumber);
        }
        return new Reservation(reservation);
    }
}
