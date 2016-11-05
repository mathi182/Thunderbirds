package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.InvalidFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerAlreadyCheckedInException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Passenger {

    private static final int AGE_MAJORITY = 18;

    private int age;
    private boolean isCheckedIn;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @JsonProperty("passenger_hash")
    private UUID passengerHash;
    @JsonProperty("first_name")
    private String firstName = "";
    @JsonProperty("last_name")
    private String lastName = "";
    @JsonProperty("passport_number")
    private String passportNumber = "";
    @JsonProperty("seat_class")
    private String seatClass;

    @ManyToOne
    @JoinColumn(name = "reservationNumber")
    @JsonBackReference
    private Reservation reservation;

    @JsonCreator
    public Passenger(@JsonProperty("first_name") String firstName,
                     @JsonProperty("last_name") String lastName,
                     @JsonProperty("age") int age,
                     @JsonProperty("passport_number") String passportNumber,
                     @JsonProperty("seat_class") String seatClass) {
        if (Strings.isNullOrEmpty(firstName)) {
            throw new InvalidFieldException("firstName");
        }
        if (Strings.isNullOrEmpty(lastName)) {
            throw new InvalidFieldException("lastName");
        }
        if (Strings.isNullOrEmpty(passportNumber)) {
            throw new InvalidFieldException("passportNumber");
        }

        this.passengerHash = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
        this.isCheckedIn = false;
    }

    protected Passenger() {
        // for hibernate
    }

    @JsonIgnore
    public static Passenger findByPassengerHash(UUID passengerHash) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        Passenger passenger = entityManager.find(Passenger.class, passengerHash);

        if (passenger == null) {
            throw new PassengerNotFoundException(passengerHash);
        }

        return passenger;
    }

    @JsonIgnore
    public UUID getId() {
        return passengerHash;
    }

    @JsonProperty("child")
    public boolean isAChild() {
        return age < AGE_MAJORITY;
    }

    public void save() {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(this);
    }

    @JsonIgnore
    public String getFlightNumber() {
        return reservation.getFlightNumber();
    }

    @JsonIgnore
    public Instant getFlightDate() {
        return reservation.getFlightDate();
    }

    @JsonIgnore
    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    @Ignore
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @JsonIgnore
    public void checkin() {
        if (isCheckedIn) {
            throw new PassengerAlreadyCheckedInException(passengerHash);
        }
        isCheckedIn = true;
    }

}