package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
public class Flight {
    @EmbeddedId
    private FlightId flightId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Plane plane;

    public Flight(FlightId flightId, Plane plane, List<Seat> seats) {
        this.flightId = flightId;
        this.plane = plane;
        this.seats = seats;
    }

    protected Flight() {
        // for hibernate
    }

    public FlightId getId() {
        return flightId;
    }

    public Seat assignBestSeat(SeatAssignationStrategy strategy, Passenger passenger) {
        Seat bestSeat = strategy.findBestSeat(seats, passenger);
        bestSeat.markAsUnavailable();
        return bestSeat;
    }
}