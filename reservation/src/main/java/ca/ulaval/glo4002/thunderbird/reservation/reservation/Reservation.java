package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.InvalidFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Reservation {
    private static final String RESERVATION_DATE_FORMAT = "yyyy-MM-dd";

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private int reservationNumber;
    @NotBlank
    private String flightNumber;
    private Instant flightDate;
    @JsonIgnore
    private Instant reservationDate;
    @NotBlank
    @JsonIgnore
    private String reservationConfirmation;
    @NotBlank
    @JsonIgnore
    private String paymentLocation;

    @Valid
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "reservation")
    @JsonManagedReference
    private List<Passenger> passengers;

    @JsonCreator
    public Reservation(int reservationNumber,
                       String reservationDate,
                       String reservationConfirmation,
                       String flightNumber,
                       Instant flightDate,
                       String paymentLocation,
                       ArrayList<Passenger> passengers) {
        this.reservationNumber = reservationNumber;
        this.reservationDate = reservationDateStringToInstant(reservationDate);
        this.reservationConfirmation = reservationConfirmation;
        this.paymentLocation = paymentLocation;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<>(passengers);
        this.passengers.forEach(passenger -> passenger.setReservation(this));
    }

    protected Reservation() {
        // for hibernate
    }

    public static Reservation findByReservationNumber(int reservationNumber) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        Reservation reservation = entityManager.find(Reservation.class, reservationNumber);

        if (reservation == null) {
            String reservationNumberString = Integer.toString(reservationNumber);
            throw new ReservationNotFoundException(reservationNumberString);
        }

        return reservation;
    }

    private Instant reservationDateStringToInstant(String reservation_date) {
        SimpleDateFormat format = new SimpleDateFormat(RESERVATION_DATE_FORMAT);
        try {
            return format.parse(reservation_date).toInstant();
        } catch (ParseException e) {
            throw new InvalidFieldException("reservation_date");
        }
    }

    public void save() {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(this);
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    public Instant getFlightDate() {
        return flightDate;
    }
}