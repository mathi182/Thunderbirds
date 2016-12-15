package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Flight {
    @EmbeddedId
    private FlightId flightId;

    @OneToOne(fetch = FetchType.LAZY)
    private Plane plane;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Passenger> passengers;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Seat> availableSeats;

    public Flight(FlightId flightId, Plane plane) {
        if (plane == null) {
            throw new IllegalArgumentException("plane");
        }
        this.flightId = flightId;
        this.plane = plane;
        this.availableSeats = plane.getSeats();
        this.passengers = new ArrayList<>();
    }

    protected Flight() {
        // for hibernate
    }

    public FlightId getId() {
        return flightId;
    }

    public Seat reserveSeat(SeatAssignationStrategy strategy, Passenger passenger) {
        Seat bestSeat = strategy.findBestSeat(availableSeats, passenger);
        availableSeats.remove(bestSeat);
        return bestSeat;
    }

    public boolean haveSpaceFor(Baggage baggage) {
        Mass baggagesMass = Mass.fromGrams(0);
        for (Passenger passenger : passengers) {
            baggagesMass = baggagesMass.add(passenger.calculateBagageMass());
        }
        baggagesMass = baggagesMass.add(baggage.getWeight());

        return baggagesMass.isInferiorOrEqualTo(plane.getMaximumCargoWeight());
    }
}
