package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;
import java.util.stream.Collectors;

public class CheapestSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass classType;
    private boolean childSeat;

    public CheapestSeatAssignationStrategy(Seat.SeatClass classType, boolean childSeat) {
        this.classType = classType;
        this.childSeat = childSeat;
    }

    @Override
    public Seat findAvailableSeat(List<Seat> availableSeats) {
        List<Seat> filteredSeats = filterBySeatClass(availableSeats);
        if(childSeat){
            filteredSeats = filterByExitRow(filteredSeats);
        }
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

    private List<Seat> filterByExitRow(List<Seat> availableSeats){
        List<Seat> seats;
        seats = availableSeats
                .stream()
                .filter(seat -> !seat.isExitRow())
                .collect(Collectors.toList());
        return seats;
    }

    private Seat findCheapestSeat(List<Seat> availableSeats) {
        Seat cheapestSeat = availableSeats.get(0);

        for (Seat seat : availableSeats) {
            if (seat.hasLowerPriceThan(cheapestSeat)) {
                cheapestSeat = seat;
            }
        }
        return cheapestSeat;
    }
}
