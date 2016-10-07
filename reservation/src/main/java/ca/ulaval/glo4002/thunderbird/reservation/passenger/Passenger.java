package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.DateLong;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;

public class Passenger {
    private static final HashMap<String, Passenger> passengerStore = new HashMap<>();
    private static final int AGE_MAJORITY = 18;
    private static final int NULL_RESERVATION_NUMBER = -1;
    private static final long MAX_LATE_CHECKIN_IN_MILLIS = 60 * 60 * 6 * 1000L;
    private static final long MAX_EARLY_CHECKIN_IN_MILLIS = 60 * 60 * 48 * 1000L;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    @JsonProperty("passenger_hash") private String id;
    @JsonProperty("first_name") private String firstName = "";
    @JsonProperty("last_name") private String lastName = "";
    @JsonProperty("passport_number") private String passportNumber = "";
    @JsonProperty("seat_class") private String seatClass;
    @JsonIgnore private int age;
    @JsonIgnore public int reservationNumber = NULL_RESERVATION_NUMBER;
    @JsonProperty("child") public boolean isAChild() {
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
        this(NULL_RESERVATION_NUMBER, firstName, lastName, age, passportNumber, seatClass);
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
        boolean reservationIsValid = true;
        if (reservationNumber != NULL_RESERVATION_NUMBER){
            try {
                Reservation checkinReservation = Reservation.findByReservationNumber(reservationNumber);
                reservationIsValid = true;
            } catch (ReservationNotFoundException e) {
                reservationIsValid = false;
            }
        }

        boolean passengerHasFirstName = !Strings.isNullOrEmpty(firstName);
        boolean passengerHasLastName = !Strings.isNullOrEmpty(lastName);
        boolean passengerHasPassportNumber = !Strings.isNullOrEmpty(passportNumber);
        boolean passengerHasReservationNumber = (reservationNumber != NULL_RESERVATION_NUMBER);
        return passengerHasFirstName
                && passengerHasLastName
                && passengerHasPassportNumber
                && passengerHasReservationNumber
                && reservationIsValid;
    }

    @JsonIgnore
    public boolean isValidForSelfCheckin() {
        /*
        boolean checkinIsValid = true;
        boolean reservationIsValid = this.isValidForCheckin();
        try {
            Reservation checkinReservation = Reservation.findByReservationNumber(reservationNumber);

        } catch (ReservationNotFoundException e) {
            checkinIsValid = false;
        }

        return checkinIsValid && reservationIsValid;
        */
        String flightDate = Reservation.findByReservationNumber(reservationNumber).getFlightDate();

        return isSelfCheckinOnTime(flightDate);
    }

    @JsonIgnore
    private boolean isSelfCheckinOnTime(String flightDate) {
        boolean isOnTime = true;
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            long parsedFlightDate = format.parse(flightDate.replaceAll("Z$", "+0000")).getTime();
            long maxEarlySelfCheckinDate = parsedFlightDate - MAX_EARLY_CHECKIN_IN_MILLIS;
            long maxLateSelfCheckinDate = parsedFlightDate - MAX_LATE_CHECKIN_IN_MILLIS;
            long currentTime = DateLong.getLongCurrentDate();

            isOnTime = (currentTime>maxEarlySelfCheckinDate) && (currentTime<maxLateSelfCheckinDate);
        } catch (ParseException e) {
            isOnTime = false;
        }
        return isOnTime;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }



    @JsonIgnore
    public int getReservationNumber() {
        return reservationNumber;
    }

}
