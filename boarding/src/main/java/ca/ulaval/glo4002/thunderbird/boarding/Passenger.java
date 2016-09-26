package ca.ulaval.glo4002.thunderbird.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.domain.Identity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static ca.ulaval.glo4002.thunderbird.boarding.Util.isStringNullOrEmpty;

public class Passenger extends Identity {

    @JsonProperty("first_name")
    public String firstName = "";
    @JsonProperty("last_name")
    public String lastName = "";
    @JsonProperty("passport_number")
    public String passportNumber = "";
    @JsonProperty("seat_class")
    public String seatClass;
    @JsonProperty("passenger_hash")
    public String hash = super.id;
    public boolean child;
    public transient int age;
    public transient int reservationNumber = -1;

    @JsonCreator
    public Passenger(@JsonProperty("firstName") String firstName,
                     @JsonProperty("lastName") String lastName,
                     @JsonProperty("age") int age,
                     @JsonProperty("passportNumber") String passportNumber,
                     @JsonProperty("seatClass") String seatClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
    }

    public Passenger(int reservationNumber, String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this.reservationNumber = reservationNumber;
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

    @JsonIgnore
    public boolean isValidForCheckin() {
        // TODO : Valider la réservation(reservationNumber) avec la ressource GET reservation
        boolean passengerHasFirstName = !isStringNullOrEmpty(firstName);
        boolean passengerHasLastName = !isStringNullOrEmpty(lastName);
        boolean passengerHasPassportNumber = !isStringNullOrEmpty(passportNumber);
        return passengerHasFirstName && passengerHasLastName && passengerHasPassportNumber;
    }
}
