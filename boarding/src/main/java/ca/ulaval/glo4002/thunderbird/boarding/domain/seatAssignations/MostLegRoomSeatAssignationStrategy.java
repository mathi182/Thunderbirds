package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.ArrayList;
import java.util.List;

public class MostLegRoomSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.seatClass classType;

    public MostLegRoomSeatAssignationStrategy(Seat.seatClass classType) {
        this.classType = classType;
    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {

//        if (availableSeats.size() == 0) {
//            throw new NoMoreSeatAvailableException();
//        }

        Seat mostLegRoomSeat = null;
//        List<Seat> filtered = new ArrayList<>();

        int mostLegRoom = 0;

        for (Seat seat : availableSeats) {
            int seatLegRoom = seat.getLegRoom();

           if (mostLegRoomSeat == null) {
                mostLegRoomSeat = seat;
           }
           else if (seat.getLegRoom() > mostLegRoomSeat.getLegRoom()) {
               mostLegRoom = seat;
           }
        }

        return mostLegRoomSeat;
    }
}
