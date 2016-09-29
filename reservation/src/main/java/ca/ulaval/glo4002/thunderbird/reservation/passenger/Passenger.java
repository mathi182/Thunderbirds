package ca.ulaval.glo4002.thunderbird.reservation.passenger;
import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.UUID;

public class Passenger {
    private static final HashMap<String, Passenger> passengerStore = new HashMap<>();
    private static final int AGE_MAJORITY = 18;

    @JsonProperty("passenger_hash") public String id;
    @JsonProperty("first_name") public String firstName = "";
    @JsonProperty("last_name") public String lastName = "";
    @JsonProperty("passport_number") public String passportNumber = "";
    @JsonProperty("seat_class") public String seatClass;
    @JsonIgnore public int age;
    @JsonIgnore public int reservationNumber = -1;

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
        this(0, firstName, lastName, age, passportNumber, seatClass);
    }

    public static synchronized Passenger findByPassengerHash(String passengerHash) {
        Passenger reservation = passengerStore.get(passengerHash);
        if (reservation == null) {
            throw new PassengerNotFoundException(passengerHash);
        }
        return reservation;
    }

    public synchronized void save() {
        passengerStore.put(id, this);
    }

    @JsonIgnore
    public boolean isValidForCheckin() {
        // TODO : Valider la réservation(reservationNumber) avec la ressource GET reservation
        boolean passengerHasFirstName = !Strings.isNullOrEmpty(firstName);
        boolean passengerHasLastName = !Strings.isNullOrEmpty(lastName);
        boolean passengerHasPassportNumber = !Strings.isNullOrEmpty(passportNumber);
        return passengerHasFirstName && passengerHasLastName && passengerHasPassportNumber;
    }
}
