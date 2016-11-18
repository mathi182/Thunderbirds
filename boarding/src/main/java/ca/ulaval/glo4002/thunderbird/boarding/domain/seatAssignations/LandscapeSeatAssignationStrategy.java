package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;

public class LandscapeSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass chosenSeatClass;

    public LandscapeSeatAssignationStrategy() {
        chosenSeatClass = Seat.SeatClass.ANY;
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
        availableSeats.removeIf(seat -> !seatIsCorrectClass(seat));

        return availableSeats;
    }

    private Seat findBestViewSeat(List<Seat> availableSeats) {
        if (availableSeats.isEmpty()) {
            throw new NoMoreSeatAvailableException();
        }

        Seat bestSeat = availableSeats.get(0);

        for (Seat seat : availableSeats) {
            if(seat.hasSameViewAs(bestSeat)){
                bestSeat = getCheapestSeat(seat, bestSeat);
            }
            else{
                if(seat.hasBetterViewThan(bestSeat)){
                    bestSeat = seat;
                }
            }
        }

        return bestSeat;
    }

    private Seat getCheapestSeat(Seat first, Seat second){
        if(first.hasLowerPriceThan(second)){
            return first;
        }
        else{
            return second;
        }
    }

    private boolean seatIsCorrectClass(Seat seat) {
        return chosenSeatClass.equals(Seat.SeatClass.ANY) || seat.getSeatClass().equals(chosenSeatClass);
    }
}
