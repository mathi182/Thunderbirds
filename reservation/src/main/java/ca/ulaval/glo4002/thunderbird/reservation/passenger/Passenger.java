package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.MissingCheckinInformationException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public class Passenger {
    private static final HashMap<String, Passenger> passengerStore = new HashMap<>();
    private static final int AGE_MAJORITY = 18;
    private static final int NULL_RESERVATION_NUMBER = -1;

    @JsonProperty("passenger_hash")
    private String id;
    @JsonProperty("first_name")
    private String firstName = "";
    @JsonProperty("last_name")
    private String lastName = "";
    @JsonProperty("passport_number")
    private String passportNumber = "";
    @JsonProperty("seat_class")
    private String seatClass;
    private int age;
    private boolean isCheckedIn;

    @JsonIgnore
    private int reservationNumber = NULL_RESERVATION_NUMBER;

    @JsonCreator
    public Passenger(@JsonProperty("reservation_number") int reservationNumber,
                     @JsonProperty("first_name") String firstName,
                     @JsonProperty("last_name") String lastName,
                     @JsonProperty("age") int age,
                     @JsonProperty("passport_number") String passportNumber,
                     @JsonProperty("seat_class") String seatClass) {
        this.id = UUID.randomUUID().toString();
        this.reservationNumber = reservationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
        this.isCheckedIn = false;
    }

    public Passenger(String firstName, String lastName, int age, String
            passportNumber, String seatClass) {
        this(NULL_RESERVATION_NUMBER, firstName, lastName, age, passportNumber, seatClass);
    }

    @JsonIgnore
    public static Passenger findByPassengerHash(String passengerHash) {
        Passenger passenger = passengerStore.get(passengerHash);
        if (passenger == null) {
            throw new PassengerNotFoundException(passengerHash);
        }
        return passenger;
    }

    @JsonProperty("child")
    public boolean isAChild() {
        return age < AGE_MAJORITY;
    }

    public synchronized void save() {
        passengerStore.put(this.id, this);
    }

    @JsonIgnore
    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    @JsonIgnore
    public String getFlightNumber() {
        return Reservation.findByReservationNumber(reservationNumber).getFlightNumber();
    }

    @JsonIgnore
    public Instant getFlightDate() {
        return Reservation.findByReservationNumber(reservationNumber).getFlightDate();
    }

    @JsonIgnore
    public void checkin() {
        if (!Reservation.reservationExists(reservationNumber)) {
            throw new ReservationNotFoundException(reservationNumber);
        }

        if(isCheckedIn){
            throw new PassengerAlreadyCheckedInException(id);
        }
        if (Strings.isNullOrEmpty(firstName)) {
            throw new MissingCheckinInformationException("firstName");
        }
        if (Strings.isNullOrEmpty(lastName)) {
            throw new MissingCheckinInformationException("lastName");
        }
        if (Strings.isNullOrEmpty(passportNumber)) {
            throw new MissingCheckinInformationException("passportNumber");
        }

        isCheckedIn = true;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }
}
