package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.InvalidFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

@Entity
public class Reservation {
    private static final String RESERVATION_DATE_FORMAT = "yyyy-MM-dd";

    @Id
    private int reservationNumber;
    private Instant flightDate;
    private Instant reservationDate;
    private String reservationConfirmation;
    private String paymentLocation;
    private String flightNumber;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "reservation")
    private List<Passenger> passengers;

    @JsonCreator
    public Reservation(@JsonProperty("reservation_number") int reservationNumber,
                       @JsonProperty("reservation_date") String reservationDate,
                       @JsonProperty("reservation_confirmation") String reservationConfirmation,
                       @JsonProperty("flight_number") String flightNumber,
                       @JsonProperty("flight_date") String flightDate,
                       @JsonProperty("payment_location") String paymentLocation,
                       @JsonProperty("passengers") ArrayList<Passenger> passengers) {
        this.reservationNumber = reservationNumber;
        this.reservationDate = reservationDateStringToInstant(reservationDate);
        this.reservationConfirmation = reservationConfirmation;
        this.paymentLocation = paymentLocation;
        this.flightDate = ISO_INSTANT.parse(flightDate, Instant::from);
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<>(passengers);
        this.passengers.forEach(passenger -> passenger.setReservation(this));
    }

    private Instant reservationDateStringToInstant(String reservation_date){
        SimpleDateFormat format = new SimpleDateFormat(RESERVATION_DATE_FORMAT);
        try{
            return format.parse(reservation_date).toInstant();
        }
        catch (ParseException e){
            throw new InvalidFieldException("reservation_date");
        }
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

    public void save() {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        entityManagerProvider.executeInTransaction(() -> entityManager.persist(this));
    }

    @JsonProperty("reservation_number")
    public int getReservationNumber() {
        return reservationNumber;
    }

    @JsonProperty("flight_number")
    public String getFlightNumber() {
        return flightNumber;
    }

    @JsonProperty("flight_date")
    public String getFormattedFlightDate() {
        return ISO_INSTANT.format(flightDate);
    }

    @JsonProperty("passengers")
    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    @JsonIgnore
    public Instant getFlightDate() {
        return flightDate;
    }
}
