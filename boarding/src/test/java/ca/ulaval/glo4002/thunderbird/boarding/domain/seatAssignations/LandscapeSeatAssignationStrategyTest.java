package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.*;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class LandscapeSeatAssignationStrategyTest {

    private LandscapeSeatAssignationStrategy strategy;
    private List<Seat> seats;
    private List<Seat> businessSeats;
    private Seat bestViewBusinessSeat;
    private Seat bestViewEconomicSeat;
    private Seat goodViewBusinessSeat;
    private Seat goodViewEconomicSeat;
    private Seat expensiveSeatMock;
    private Seat cheapSeatMock;

    @Before
    public void before() {
        seats = new ArrayList<>();
        businessSeats = new ArrayList<>();

        bestViewBusinessSeat = mock(Seat.class);
        bestViewEconomicSeat = mock(Seat.class);
        goodViewBusinessSeat = mock(Seat.class);
        goodViewEconomicSeat = mock(Seat.class);
        expensiveSeatMock = mock(Seat.class);
        cheapSeatMock = mock(Seat.class);
        givenAValidSeatsList();
    }

    private void givenAValidSeatsList() {
        willReturn(Seat.SeatClass.BUSINESS).given(bestViewBusinessSeat).getSeatClass();
        willReturn(Seat.SeatClass.BUSINESS).given(goodViewBusinessSeat).getSeatClass();
        willReturn(Seat.SeatClass.ECONOMY).given(bestViewEconomicSeat).getSeatClass();
        willReturn(Seat.SeatClass.ECONOMY).given(goodViewEconomicSeat).getSeatClass();

        seats.addAll(Arrays.asList(bestViewBusinessSeat, bestViewEconomicSeat,
                goodViewBusinessSeat, goodViewEconomicSeat));
        businessSeats.addAll(Arrays.asList(bestViewBusinessSeat, goodViewBusinessSeat));
    }

    @Test
    public void givenAValidSeatsList_whenSelectingBestLandscape_shouldReturnBestFromAnyClass() {
        willReturn(true).given(bestViewEconomicSeat).hasBetterViewThan(bestViewBusinessSeat);
        strategy = new LandscapeSeatAssignationStrategy(Seat.SeatClass.ANY);

        Seat actualSeat = strategy.assignSeat(seats);

        assertEquals(bestViewEconomicSeat, actualSeat);
    }

    @Test
    public void givenAValidSeatsList_whenSelectingBestLandscapeFromBusiness_shouldReturnBestFromBusiness() {
        strategy = new LandscapeSeatAssignationStrategy(Seat.SeatClass.BUSINESS);
        Seat actualSeat = strategy.assignSeat(seats);

        assertEquals(bestViewBusinessSeat, actualSeat);
    }

    @Test(expected = NoMoreSeatAvailableException.class)
    public void givenAnEmptyList_whenSelectingBestLandscape_shouldThrowNoMoreSeatException() {
        strategy = new LandscapeSeatAssignationStrategy(Seat.SeatClass.ANY);
        seats.clear();

        strategy.assignSeat(seats);
    }

    @Test(expected = NoMoreSeatAvailableException.class)
    public void
    givenAValidListWithoutEconomicSeat_whenSelectingBestLandscapeFromEconomic_shouldThrowNoMoreSeatException() {
        strategy = new LandscapeSeatAssignationStrategy(Seat.SeatClass.ECONOMY);

        strategy.assignSeat(businessSeats);
    }

    @Test
    public void givenAValidListWithSameView_whenSelectingBestLandscapeFromEconomic_shouldReturnCheapestSeat() {
        willReturn(true).given(cheapSeatMock).hasSameViewAs(expensiveSeatMock);
        willReturn(false).given(cheapSeatMock).hasBetterViewThan(expensiveSeatMock);
        willReturn(true).given(cheapSeatMock).hasLowerPriceThan(any(Seat.class));

        seats.clear();
        seats.add(expensiveSeatMock);
        seats.add(cheapSeatMock);
        strategy = new LandscapeSeatAssignationStrategy(Seat.SeatClass.ANY);

        Seat actualSeat = strategy.assignSeat(seats);

        Seat expectedSeat = cheapSeatMock;
        assertEquals(expectedSeat, actualSeat);
    }
}
