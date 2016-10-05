package ca.ulaval.glo4002.thunderbird.reservation.passenger;
import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.UUID;

public class PassengerStorage {
    private static final HashMap<String, PassengerStorage> passengerStore = new HashMap<>();
    private static final int AGE_MAJORITY = 18;

    @JsonProperty("passenger_hash") private String id;
    @JsonProperty("first_name") private String firstName = "";
    @JsonProperty("last_name") private String lastName = "";
    @JsonProperty("passport_number") private String passportNumber = "";
    @JsonProperty("seat_class") private String seatClass;
    private int age;
    private int reservationNumber = -1;

    @JsonProperty("child")
    public boolean isAChild() {
        return age < AGE_MAJORITY;
    }

    @JsonCreator
    public PassengerStorage(@JsonProperty("reservation_number") int reservationNumber,
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

    public PassengerStorage(String firstName, String lastName, int age, String passportNumber, String seatClass) {
        this(0, firstName, lastName, age, passportNumber, seatClass);
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

    @JsonIgnore
    public static synchronized PassengerStorage findByPassengerHash(String passengerHash) {
        PassengerStorage passenger = passengerStore.get(passengerHash);
        if (passenger == null) {
            throw new PassengerNotFoundException(passengerHash);
        }
        return passenger;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    @JsonIgnore
    public int getReservationNumber() {
        return reservationNumber;
    }

    @JsonIgnore
    public String getId(){
        return id;
    }
}
