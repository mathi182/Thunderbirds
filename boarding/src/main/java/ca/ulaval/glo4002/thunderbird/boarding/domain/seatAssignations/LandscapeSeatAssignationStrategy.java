package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.ArrayList;
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
        availableSeats = filterSeatsByClass(availableSeats);
        Seat bestSeat = findBestViewSeat(availableSeats);

        return bestSeat;
    }

    private List<Seat> filterSeatsByClass(List<Seat> availableSeats) {
        if (chosenSeatClass != Seat.SeatClass.ANY) {
            return availableSeats
                    .stream()
                    .filter(seat -> seat.getSeatClass().equals(chosenSeatClass))
                    .collect(Collectors.toList());
        } else {
            return availableSeats;
        }
    }

    private Seat findBestViewSeat(List<Seat> availableSeats) {
        if (availableSeats.isEmpty()) {
            throw new NoMoreSeatAvailableException();
        }

        Seat bestSeat = getBestViewSeat(availableSeats);
        List<Seat> bestSeatsList = getBestSeats(availableSeats, bestSeat);
        return chooseSeat(bestSeatsList);
    }

    private Seat getBestViewSeat(List<Seat> availableSeats) {
        Seat bestSeat = availableSeats.get(0);
        for (Seat seat : availableSeats) {
            if (seat.hasBetterViewThan(bestSeat)) {
                bestSeat = seat;
            }
        }
        return bestSeat;
    }

    private List<Seat> getBestSeats(List<Seat> availableSeats, Seat bestSeat) {
        List<Seat> bestSeatsList = new ArrayList<>();
        bestSeatsList.add(bestSeat);
        for (Seat seat : availableSeats) {
            if (seat.hasSameViewAs(bestSeat)) {
                bestSeatsList.add(seat);
            }
        }
        return bestSeatsList;
    }

    private Seat chooseSeat(List<Seat> bestSeatsList) {
        if (bestSeatsList.size() > 1) {
            return new CheapestSeatAssignationStrategy(chosenSeatClass).assignSeat(bestSeatsList);
        }
        return bestSeatsList.get(0);
    }

    private boolean seatIsCorrectClass(Seat seat) {
        return chosenSeatClass.equals(Seat.SeatClass.ANY) || seat.getSeatClass().equals(chosenSeatClass);
    }
}
