package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MostLegRoomSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass classType;
    private boolean childSeat;

    public MostLegRoomSeatAssignationStrategy(Seat.SeatClass classType, boolean childSeat) {
        this.classType = classType;
        this.childSeat = childSeat;
    }

    @Override
    public Seat findAvailableSeat(List<Seat> availableSeats) {
        List<Seat> filteredSeats = filterBySeatClass(availableSeats);

        if (filteredSeats.isEmpty()) {
            throw new NoMoreSeatAvailableException();
        }

        filteredSeats = filterByLegRoom(filteredSeats);

        return getBestSeat(filteredSeats);
    }

    private List<Seat> filterBySeatClass(List<Seat> availableSeats) {
        if (classType != Seat.SeatClass.ANY) {
            return availableSeats
                    .stream()
                    .filter(seat -> seat.getSeatClass().equals(classType))
                    .collect(Collectors.toList());
        }
        return availableSeats;

    }

    private List<Seat> filterByLegRoom(List<Seat> filteredSeats) {
        Seat currentMostLegRoomSeat = filteredSeats.get(0);
        List<Seat> seats = new ArrayList<>(Collections.singletonList(currentMostLegRoomSeat));

        for (int i = 1; i < filteredSeats.size(); i++) {
            Seat seat = filteredSeats.get(i);
            if (seat.hasMoreLegRoomThan(currentMostLegRoomSeat)) {
                currentMostLegRoomSeat = seat;

                seats.clear();
                seats.add(seat);
            } else if (seat.hasSameAmountOfLegRoomAs(currentMostLegRoomSeat)) {
                seats.add(seat);
            }
        }
        return seats;
    }

    private Seat getBestSeat(List<Seat> seats) {
        if (seats.size() > 1) {
            CheapestSeatAssignationStrategy strategy = new CheapestSeatAssignationStrategy(classType, childSeat);
            return strategy.findAvailableSeat(seats);
        }
        return seats.get(0);
    }
}
