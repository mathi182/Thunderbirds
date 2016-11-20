package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
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

    private void mockSeatsForAnyClass() {
        mockEconomySeats();
        mockBusinessSeats();
    }

    private void mockSeatsByClass() {
        willReturn(bestViewEconomicSeat).given(bestViewEconomicSeat).bestSeatViewBetween(any(Seat.class));
        willReturn(bestViewBusinessSeat).given(bestViewBusinessSeat).bestSeatViewBetween(any(Seat.class));
        willReturn(goodViewBusinessSeat).given(goodViewBusinessSeat).bestSeatViewBetween(any(Seat.class));
        willReturn(bestViewBusinessSeat).given(goodViewBusinessSeat).bestSeatViewBetween(bestViewBusinessSeat);
        willReturn(goodViewEconomicSeat).given(goodViewEconomicSeat).bestSeatViewBetween(any(Seat.class));
        willReturn(bestViewEconomicSeat).given(goodViewEconomicSeat).bestSeatViewBetween(bestViewEconomicSeat);
    }

    @Test
    public void givenAValidSeatsList_whenSelectingBestLandscape_shouldReturnBestFromAnyClass() {
        mockSeatsForAnyClass();
        strategy = new LandscapeSeatAssignationStrategy(Seat.SeatClass.ANY);

        Seat actualSeat = strategy.assignSeat(seats);

        assertEquals(bestViewEconomicSeat, actualSeat);
    }

    @Test
    public void givenAValidSeatsList_whenSelectingBestLandscapeFromBusiness_shouldReturnBestFromBusiness() {
        mockSeatsByClass();
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
        willReturn(true).given(expensiveSeatMock).hasSameViewAs(cheapSeatMock);
        willReturn(true).given(cheapSeatMock).hasSameViewAs(expensiveSeatMock);
        willReturn(true).given(cheapSeatMock).hasLowerPriceThan(any(Seat.class));

        seats.clear();
        seats.add(expensiveSeatMock);
        seats.add(cheapSeatMock);
        strategy = new LandscapeSeatAssignationStrategy(Seat.SeatClass.ANY);

        Seat actualSeat = strategy.assignSeat(seats);

        Seat expectedSeat = cheapSeatMock;
        assertEquals(expectedSeat, actualSeat);
    }

    private void mockBusinessSeats() {
        willReturn(true).given(bestViewBusinessSeat).hasBetterViewThan(goodViewBusinessSeat);
        willReturn(false).given(bestViewBusinessSeat).hasBetterViewThan(bestViewBusinessSeat);
        willReturn(false).given(goodViewBusinessSeat).hasBetterViewThan(goodViewBusinessSeat);
        willReturn(false).given(goodViewBusinessSeat).hasBetterViewThan(bestViewBusinessSeat);

        willReturn(true).given(bestViewBusinessSeat).hasSameViewAs(bestViewBusinessSeat);
        willReturn(false).given(bestViewBusinessSeat).hasSameViewAs(goodViewBusinessSeat);
        willReturn(false).given(goodViewBusinessSeat).hasSameViewAs(bestViewBusinessSeat);
        willReturn(true).given(goodViewBusinessSeat).hasSameViewAs(goodViewBusinessSeat);
    }

    private void mockEconomySeats() {
        willReturn(true).given(bestViewEconomicSeat).hasBetterViewThan(goodViewEconomicSeat);
        willReturn(false).given(bestViewEconomicSeat).hasBetterViewThan(bestViewEconomicSeat);
        willReturn(false).given(goodViewEconomicSeat).hasBetterViewThan(goodViewEconomicSeat);
        willReturn(false).given(goodViewEconomicSeat).hasBetterViewThan(bestViewEconomicSeat);

        willReturn(true).given(bestViewEconomicSeat).hasSameViewAs(bestViewEconomicSeat);
        willReturn(false).given(bestViewEconomicSeat).hasSameViewAs(goodViewEconomicSeat);
        willReturn(false).given(goodViewEconomicSeat).hasSameViewAs(bestViewEconomicSeat);
        willReturn(true).given(goodViewEconomicSeat).hasSameViewAs(goodViewEconomicSeat);

        willReturn(true).given(bestViewEconomicSeat).hasBetterViewThan(bestViewBusinessSeat);
        willReturn(true).given(bestViewEconomicSeat).hasBetterViewThan(goodViewBusinessSeat);
        willReturn(true).given(goodViewEconomicSeat).hasBetterViewThan(bestViewBusinessSeat);
        willReturn(true).given(goodViewEconomicSeat).hasBetterViewThan(goodViewBusinessSeat);

        willReturn(false).given(bestViewEconomicSeat).hasSameViewAs(bestViewBusinessSeat);
        willReturn(false).given(bestViewEconomicSeat).hasSameViewAs(goodViewBusinessSeat);
        willReturn(false).given(goodViewEconomicSeat).hasSameViewAs(bestViewBusinessSeat);
        willReturn(false).given(goodViewEconomicSeat).hasSameViewAs(goodViewBusinessSeat);
    }
}
