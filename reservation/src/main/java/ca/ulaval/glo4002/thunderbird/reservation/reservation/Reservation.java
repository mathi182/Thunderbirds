package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.InvalidFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class Reservation {
    private static final HashMap<Integer, Reservation> reservationStore = new HashMap<>();
    private static final String RESERVATION_DATE_FORMAT = "yyyy-MM-dd";
    
    @JsonProperty("reservation_number") private int reservationNumber;
    @JsonProperty("flight_number") private String flightNumber;
    @JsonProperty("passengers") private ArrayList<Passenger> passengers;
    @JsonIgnore private Instant flightDate;
    @JsonIgnore private Instant reservationDate;
    @JsonIgnore private String reservationConfirmation;
    @JsonIgnore private String paymentLocation;

    @JsonCreator
    public Reservation(@JsonProperty("reservation_number") int reservationNumber,
                       @JsonProperty("reservation_date") String reservationDate,
                       @JsonProperty("reservation_confirmation") String reservationConfirmation,
                       @JsonProperty("flight_number") String flightNumber,
                       @JsonProperty("flight_date") String flightDate,
                       @JsonProperty("payment_location") String paymentLocation,
                       @JsonProperty("passengers") ArrayList<Passenger> passengers) {
        this.reservationNumber = reservationNumber;
        this.reservationDate = reservationDateStringToInstant(reservationDate);
        this.reservationConfirmation = reservationConfirmation;
        this.paymentLocation = paymentLocation;
        this.flightDate = ISO_INSTANT.parse(flightDate, Instant::from);
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<>(passengers);
        this.passengers.forEach(passenger -> passenger.setReservationNumber(reservationNumber));
    }

    private Instant reservationDateStringToInstant(String reservation_date){
        SimpleDateFormat format = new SimpleDateFormat(RESERVATION_DATE_FORMAT);
        try{
            return format.parse(reservation_date).toInstant();
        }
        catch (ParseException e){
            throw new InvalidFieldException("reservation_date");
        }
    }

    public static void resetReservationStore() {
        reservationStore.clear();
    }

    public static Reservation findByReservationNumber(int reservationNumber) {
        Reservation reservation = reservationStore.get(reservationNumber);
        if (reservation == null) {
            String reservationNumberString = Integer.toString(reservationNumber);
            throw new ReservationNotFoundException(reservationNumberString);
        }
        return reservation;
    }

    public static boolean reservationExists(int reservationNumber) {
        Reservation reservation = reservationStore.get(reservationNumber);
        return reservation != null;
    }

    @JsonProperty("flight_date")
    public String getFormattedFlightDate() {
        return ISO_INSTANT.format(flightDate);
    }

    public void save() {
        reservationStore.put(reservationNumber, this);
        passengers.forEach(passenger -> passenger.save());
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Instant getFlightDate() {
        return flightDate;
    }
}
