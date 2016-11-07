package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MostLegRoomSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass classType;

    public MostLegRoomSeatAssignationStrategy(Seat.SeatClass classType) {
        this.classType = classType;
    }

    public MostLegRoomSeatAssignationStrategy() {

    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        List<Seat> filteredSeats = filterBySeatClass(availableSeats);

        if (filteredSeats.size() == 0) {
            throw new NoMoreSeatAvailableException();
        }

        filteredSeats = filterByLegRoom(filteredSeats);

        return getBestSeat(filteredSeats);

    }

    @Override
    public void setSeatClass(Seat.SeatClass seatClass) {
        classType = seatClass;
    }

    private List<Seat> filterBySeatClass(List<Seat> availableSeats) {
        if (classType != Seat.SeatClass.ANY) {
            return availableSeats.stream().filter(seat -> seat.getSeatClass().equals(classType))
                    .collect(Collectors.toList());
        } else {
            return availableSeats;
        }
    }

    private List<Seat> filterByLegRoom(List<Seat> filteredSeats) {
        int currentMostLegRoom = 0;
        List<Seat> seats = new ArrayList<>();

        for (Seat seat : filteredSeats) {
            if (seat.hasMoreLegRoomThan(currentMostLegRoom)) {  // Remplacer par seat.getLegRoom() > currentMostLegRoom
                currentMostLegRoom = seat.getLegRoom();

                seats.clear();
                seats.add(seat);
            } else if (seat.hasSameAmountOfLegRoom(currentMostLegRoom)) {
                seats.add(seat);
            }
        }
        return seats;
    }

    private Seat getBestSeat(List<Seat> seats) {
        if (seats.size() > 1) {
            CheapestSeatAssignationStrategy strategy = new CheapestSeatAssignationStrategy(classType);
            return strategy.assignSeat(seats);
        }
        return seats.get(0);
    }
}
