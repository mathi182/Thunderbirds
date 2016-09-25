package ca.ulaval.glo4002.thunderbird.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.domain.Identity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

static ca.ulaval.glo4002.thunderbird.boarding.Util.isStringNullOrEmpty;

public class Passenger extends Identity {

    public int reservationNumber = -1;
    public String firstName = "";
    public String lastName = "";
    public int age;
    public String passportNumber = "";
    public String seatClass;

    public Passenger(int reservationNumber, String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this.reservationNumber = reservationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
    }

    public Passenger(String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
    }

    public Passenger(Passenger other) {
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.age = other.age;
        this.passportNumber = other.passportNumber;
        this.seatClass = other.seatClass;
    }

    public Passenger(int age, String seatClass) {
        this.age = age;
        this.seatClass = seatClass;
    }

    //TODO: Remove this when we will have a repository
    public static Passenger findByPassengerHash(String passengerHash) {
        throw new NotImplementedException();
    }

    public boolean isValidForCheckin() {
        // TODO : Valider la réservation(reservationNumber) avec la ressource GET reservation
        boolean passengerHasFirstName = !isStringNullOrEmpty(this.firstName);
        boolean passengerHasLastName = !isStringNullOrEmpty(this.lastName);
        boolean passengerHasPassportNumber = !isStringNullOrEmpty(this.passportNumber);
        return passengerHasFirstName && passengerHasLastName && passengerHasPassportNumber;
    }
}
