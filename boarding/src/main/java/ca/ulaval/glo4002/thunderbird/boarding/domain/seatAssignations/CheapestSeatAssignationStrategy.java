package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;
import java.util.stream.Collectors;

public class CheapestSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass classType;

    public CheapestSeatAssignationStrategy(Seat.SeatClass classType) {
        this.classType = classType;
    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        List<Seat> filteredSeats = filterBySeatClass(availableSeats);

        if (filteredSeats.size() == 0) {
            throw new NoMoreSeatAvailableException();
        }

        return findCheapestSeat(filteredSeats);
    }

    private List<Seat> filterBySeatClass(List<Seat> availableSeats) {
        List<Seat> seats;
        if (classType != Seat.SeatClass.ANY) {
            seats = availableSeats
                    .stream()
                    .filter(seat -> seat.getSeatClass().equals(classType))
                    .collect(Collectors.toList());
        } else {
            seats = availableSeats;
        }
        return seats;
    }

    private Seat findCheapestSeat(List<Seat> availableSeats) {
        Seat cheapestSeat = null;

        for (Seat seat : availableSeats) {
            if (isSeatGoodClass(seat)) {
                if (cheapestSeat == null) {
                    cheapestSeat = seat;
                } else if (seat.hasLowerPriceThan(cheapestSeat)) {
                    cheapestSeat = seat;
                }
            }
        }

        return cheapestSeat;
    }

    private boolean isSeatGoodClass(Seat seat) {
        return classType.equals(Seat.SeatClass.ANY) || seat.getSeatClass().equals(classType);
    }
}
