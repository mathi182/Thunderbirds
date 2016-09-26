package ca.ulaval.glo4002.thunderbird.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.domain.Identity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static ca.ulaval.glo4002.thunderbird.boarding.Util.isStringNullOrEmpty;

public class Passenger extends Identity {

    public transient int reservationNumber = -1;
    public String first_name = "";
    public String last_name = "";
    public boolean child;
    public transient int age;
    public String passport_number = "";
    public String seat_class;
    public String passenger_hash = super.id;

    @JsonCreator
    public Passenger(@JsonProperty("first_name") String firstName,
                     @JsonProperty("last_name") String lastName,
                     @JsonProperty("age") int age,
                     @JsonProperty("passport_number") String passportNumber,
                     @JsonProperty("seat_class") String seatClass) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.age = age;
        this.passport_number = passportNumber;
        this.seat_class = seatClass;
    }

    public Passenger(int reservationNumber, String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this.reservationNumber = reservationNumber;
        this.first_name = firstName;
        this.last_name = lastName;
        this.age = age;
        this.passport_number = passportNumber;
        this.seat_class = seatClass;
    }

    public Passenger(Passenger other) {
        this.first_name = other.first_name;
        this.last_name = other.last_name;
        this.age = other.age;
        this.passport_number = other.passport_number;
        this.seat_class = other.seat_class;
    }

    public Passenger(int age, String seatClass) {
        this.age = age;
        this.seat_class = seatClass;
    }

    //TODO: Remove this when we will have a repository
    public static Passenger findByPassengerHash(String passengerHash) {
        throw new NotImplementedException();
    }

    @JsonIgnore
    public boolean isValidForCheckin() {
        // TODO : Valider la réservation(reservationNumber) avec la ressource GET reservation
        boolean passengerHasFirstName = !isStringNullOrEmpty(this.first_name);
        boolean passengerHasLastName = !isStringNullOrEmpty(this.last_name);
        boolean passengerHasPassportNumber = !isStringNullOrEmpty(this.passport_number);
        return passengerHasFirstName && passengerHasLastName && passengerHasPassportNumber;
    }
}
