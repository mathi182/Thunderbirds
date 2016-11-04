package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

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

    protected Flight() {

    }

    public Flight(String flightNumber, Instant flightDate, Plane plane, Collection<Seat> seats) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.plane = plane;
        this.seats = new ArrayList<>(seats);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Instant getFlightDate() {
        return flightDate;
    }

    public Seat assignSeat(SeatAssignationStrategy strategy) {
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                availableSeats.add(seat);
            }
        }

        if (availableSeats.isEmpty()) {
            throw new SeatNotAvailableException(flightNumber);
        }

        Seat takenSeat = strategy.assignSeat(availableSeats);
        return takenSeat;
    }
}
