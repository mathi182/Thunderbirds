package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.ArrayList;
import java.util.List;

public class LandscapeSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass chosenSeatClass;
    private SeatAssignationStrategy lastResortStrategy;

    public LandscapeSeatAssignationStrategy() {
        this(Seat.SeatClass.ANY);
    }

    public LandscapeSeatAssignationStrategy(Seat.SeatClass seatClass) {
        this.chosenSeatClass = seatClass;
        lastResortStrategy = new CheapestSeatAssignationStrategy(chosenSeatClass);
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
                if(seat.hasBetterViewThan(bestSeat)){
                    bestSeat = seat;
                }
        }

        List<Seat> bestSeatsList = new ArrayList<>();
        bestSeatsList.add(bestSeat);
        for (Seat seat : availableSeats){
            if(seat.hasSameViewAs(bestSeat)){
                bestSeatsList.add(seat);
            }
        }

        Seat chosenSeat;
        if(bestSeatsList.size()>1){
            chosenSeat = lastResortStrategy.assignSeat(bestSeatsList);
        }
        else{
            chosenSeat = bestSeatsList.get(0);
        }

        return chosenSeat;
    }

    private boolean seatIsCorrectClass(Seat seat) {
        return chosenSeatClass.equals(Seat.SeatClass.ANY) || seat.getSeatClass().equals(chosenSeatClass);
    }
}
