package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.SeatNotAvailableException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Seat> availableSeats = seats.stream().filter(Seat::isAvailable).collect(Collectors.toList());

        if (availableSeats.isEmpty()) {
            throw new SeatNotAvailableException(flightNumber);
        }

        return strategy.assignSeat(availableSeats);
    }
}
