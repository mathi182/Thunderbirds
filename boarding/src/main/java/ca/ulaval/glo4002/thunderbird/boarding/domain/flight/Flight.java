package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.SeatNotAvailableException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String flightNumber;
    private Instant flightDate;
    @OneToMany
    @Cascade({CascadeType.ALL})
    private List<Seat> seats;
    @OneToOne
    @Cascade({CascadeType.ALL})
    private Plane plane;

    public Flight(String flightNumber, Instant flightDate, Plane plane, Collection<Seat> seats) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.plane = plane;
        this.seats = new ArrayList<>(seats);
    }

    protected Flight() {
        // for hibernate
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Instant getFlightDate() {
        return flightDate;
    }

    public Seat findAvailableSeat(SeatAssignationStrategy strategy, Passenger passenger) {
        List<Seat> availableSeats = filterSeats(passenger);
        if (availableSeats.isEmpty()) {
            throw new SeatNotAvailableException(flightNumber);
        }

        return strategy.findAvailableSeat(availableSeats);
    }

    private List<Seat> filterSeats(Passenger passenger){
        List<Seat> availableSeats = seats.stream().filter(Seat::isAvailable).collect(Collectors.toList());
        if(passenger.isAChild()){
            availableSeats = availableSeats.stream().filter(seat -> !seat.isExitRow()).collect(Collectors.toList());
        }

        return availableSeats;
    }


    public int getId() {
        return id;
    }
}
