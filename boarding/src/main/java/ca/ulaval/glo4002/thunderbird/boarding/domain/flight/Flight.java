package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.SeatNotAvailableException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Flight {

    private String flightNumber;
    private Instant flightDate;
    private List<Seat> seats;
    private Plane plane;

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
