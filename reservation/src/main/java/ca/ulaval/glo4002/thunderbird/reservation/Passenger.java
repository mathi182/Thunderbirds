package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;

import java.util.HashMap;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.reservation.Util.isStringNullOrEmpty;

public class Passenger {
    private static final HashMap<String, Passenger> passengerStore = new HashMap<>();

    public String passengerHash;
    public int reservationNumber = -1;
    public String firstName = "";
    public String lastName = "";
    public int age;
    public String passportNumber = "";
    public String seatClass;

    public Passenger(int reservationNumber, String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this(firstName, lastName, age, passportNumber, seatClass);
        this.reservationNumber = reservationNumber;
    }

    public Passenger(String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this.passengerHash = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
    }

    public static synchronized Passenger findByPassengerHash(String passengerHash) {
        Passenger reservation = passengerStore.get(passengerHash);
        if (reservation == null) {
            throw new PassengerNotFoundException(passengerHash);
        }
        return reservation;
    }

    public synchronized void save() {
        passengerStore.put(this.passengerHash, this);
    }

    public boolean isValidForCheckin() {
        // TODO : Valider la réservation(reservationNumber) avec la ressource GET reservation
        boolean passengerHasFirstName = !isStringNullOrEmpty(this.firstName);
        boolean passengerHasLastName = !isStringNullOrEmpty(this.lastName);
        boolean passengerHasPassportNumber = !isStringNullOrEmpty(this.passportNumber);
        return passengerHasFirstName && passengerHasLastName && passengerHasPassportNumber;
    }
}
