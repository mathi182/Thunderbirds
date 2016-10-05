package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javassist.bytecode.stackmap.BasicBlock;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class Passenger {
    private static final HashMap<String, Passenger> passengerStore = new HashMap<>();
    private static final int AGE_MAJORITY = 18;
    private static final int INITIAL_RESERVATION_NUMBER = -1;

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
    @JsonIgnore
    private int age;
    @JsonIgnore
    public int reservationNumber = INITIAL_RESERVATION_NUMBER;

    @JsonProperty("child")
    public boolean isAChild() {
        return age < AGE_MAJORITY;
    }

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
    }

    public Passenger(String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this(INITIAL_RESERVATION_NUMBER, firstName, lastName, age, passportNumber, seatClass);
    }

    public static synchronized Passenger findByPassengerHash(String passengerHash) {
        Passenger reservation = passengerStore.get(passengerHash);
        if (reservation == null) {
            throw new PassengerNotFoundException(passengerHash);
        }
        return reservation;
    }

    public synchronized void save() {
        if (passengerStore.containsKey(this.id)) {
            throw new PassengerAlreadySavedException(this.id);
        }
        passengerStore.put(this.id, this);
    }

    @JsonIgnore
    public boolean isValidForCheckin() {
        if (reservationNumber != INITIAL_RESERVATION_NUMBER) try {
            Reservation.findByReservationNumber(reservationNumber);
        } catch (ReservationNotFoundException e) {
            return false;
        }
        boolean passengerHasFirstName = !Strings.isNullOrEmpty(firstName);
        boolean passengerHasLastName = !Strings.isNullOrEmpty(lastName);
        boolean passengerHasPassportNumber = !Strings.isNullOrEmpty(passportNumber);
        boolean passengerHasReservationNumber = (reservationNumber != INITIAL_RESERVATION_NUMBER);
        return passengerHasFirstName && passengerHasLastName && passengerHasPassportNumber && passengerHasReservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    @JsonIgnore
    public int getReservationNumber() {
        return reservationNumber;
    }
}
