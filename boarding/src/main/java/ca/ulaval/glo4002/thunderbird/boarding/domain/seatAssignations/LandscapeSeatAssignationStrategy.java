package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.*;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LandscapeSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass chosenSeatClass;

    public LandscapeSeatAssignationStrategy() {
        this(Seat.SeatClass.ANY);
    }

    public LandscapeSeatAssignationStrategy(Seat.SeatClass seatClass) {
        this.chosenSeatClass = seatClass;
    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        List<Seat> filteredSeats = filterSeatsByClass(availableSeats);
        if (filteredSeats.isEmpty()) {
            throw new NoMoreSeatAvailableException();
        }

        filteredSeats = filterBestViewSeat(filteredSeats);

        return chooseSeat(filteredSeats);
    }

    private List<Seat> filterSeatsByClass(List<Seat> availableSeats) {
        if (chosenSeatClass != Seat.SeatClass.ANY) {
            return availableSeats
                    .stream()
                    .filter(seat -> seat.getSeatClass().equals(chosenSeatClass))
                    .collect(Collectors.toList());
        }
        return availableSeats;
    }

    private List<Seat> filterBestViewSeat(List<Seat> availableSeats) {
        Seat currentBestSeat = availableSeats.get(0);
        List<Seat> seats = new ArrayList<>(Collections.singletonList(currentBestSeat));

        for (int i = 1; i < availableSeats.size(); i++) {
            Seat seat = availableSeats.get(i);
            if (seat.hasBetterViewThan(currentBestSeat)) {
                currentBestSeat = seat;

                seats.clear();
                seats.add(seat);
            } else if (seat.hasSameViewAs(currentBestSeat)) {
                seats.add(seat);
            }
        }

        return seats;
    }

    private Seat chooseSeat(List<Seat> seats) {
        if (seats.size() > 1) {
            CheapestSeatAssignationStrategy strategy = new CheapestSeatAssignationStrategy(chosenSeatClass);
            return strategy.assignSeat(seats);
        }
        return seats.get(0);
    }

}