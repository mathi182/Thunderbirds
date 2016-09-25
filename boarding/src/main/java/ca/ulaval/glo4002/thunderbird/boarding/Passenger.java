package ca.ulaval.glo4002.thunderbird.boarding;

import javassist.NotFoundException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;

import static ca.ulaval.glo4002.thunderbird.boarding.Util.isStringNullOrEmpty;


public class Passenger {
    private static final HashMap<String, Passenger> passengerStore = new HashMap<>();

    public int reservationNumber = -1;
    public String firstName = "";
    public String lastName = "";
    public int age;
    public String passportNumber = "";
    public String seatClass;
    private String hash = "";
    private HashSet<String> hashRepository = new HashSet<>();

    public Passenger(int reservationNumber, String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this.reservationNumber = reservationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
        this.hash = hash;
        this.hashRepository = hashRepository;
        this.generateHash();
    }

    public Passenger(String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
        this.generateHash();
    }

    public Passenger(Passenger other) {
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.age = other.age;
        this.passportNumber = other.passportNumber;
        this.seatClass = other.seatClass;
        this.hash = other.getHash();
    }

    public Passenger(int age, String seatClass) {
        this.age = age;
        this.seatClass = seatClass;
        this.generateHash();
    }

    private void generateHash() {
        String possibleHash;
        int nbTries = 0;
        do {
            SecureRandom random = new SecureRandom();
            possibleHash = new BigInteger(130, random).toString(32);
            if (++nbTries > 100)
                throw new RuntimeException("Could not find an available hash in 100 tries.");
        } while (hashRepository.contains(possibleHash));
        hashRepository.add(possibleHash);
        this.hash = possibleHash;
    }

    public static Passenger findByPassengerHash(String passengerHash) {
        throw new NotImplementedException();
    }

    public String getHash() {
        return hash;
    }

    public boolean isValidForCheckin() {
        // TODO : Valider la réservation(reservationNumber) avec la ressource GET reservation
        boolean passengerHasFirstName = !isStringNullOrEmpty(this.firstName);
        boolean passengerHasLastName = !isStringNullOrEmpty(this.lastName);
        boolean passengerHasPassportNumber = !isStringNullOrEmpty(this.passportNumber);
        return passengerHasFirstName && passengerHasLastName && passengerHasPassportNumber;
    }
}
