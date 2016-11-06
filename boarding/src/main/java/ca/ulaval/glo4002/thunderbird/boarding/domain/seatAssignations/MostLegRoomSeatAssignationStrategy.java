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

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        List<Seat> filteredSeats;
        if (classType != Seat.SeatClass.ANY) {
            filteredSeats = availableSeats.stream().filter(seat -> seat.getSeatClass().equals(classType))
                    .collect(Collectors.toList());
        } else {
            filteredSeats = availableSeats;
        }

        if (filteredSeats.size() == 0) {
            throw new NoMoreSeatAvailableException();
        }

        Seat mostLegRoomSeat = filteredSeats.get(0);
        int mostLegRoom = mostLegRoomSeat.getLegRoom();
        Seat seatLegRoom;

        for (int i = 1; i < filteredSeats.size(); i++) {
            seatLegRoom = filteredSeats.get(i);

            if (seatLegRoom.getLegRoom() > mostLegRoom) {
                mostLegRoom = seatLegRoom.getLegRoom();
                mostLegRoomSeat = seatLegRoom;
            }
        }

        return mostLegRoomSeat;
    }
}
