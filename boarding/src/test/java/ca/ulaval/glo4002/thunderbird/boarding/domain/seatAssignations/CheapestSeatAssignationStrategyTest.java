package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class CheapestSeatAssignationStrategyTest {

    private CheapestSeatAssignationStrategy strategy;
    private List<Seat> seats;
    Seat cheapestBusinessSeat;
    Seat cheapestEconomicSeat;
    Seat normalBusinessSeat;
    Seat normalEconomicSeat;
    private static final double CHEAPEST_BUSINESS_PRICE = 100.0;
    private static final double CHEAPEST_ECONOMIC_PRICE = 50.0;
    private static final double NORMAL_BUSINESS_PRICE = 150.0;
    private static final double NORMAL_ECONOMIC_PRICE = 80.0;

    @Before
    public void before() {
        cheapestBusinessSeat = mock(Seat.class);
        cheapestEconomicSeat = mock(Seat.class);
        normalBusinessSeat = mock(Seat.class);
        normalEconomicSeat = mock(Seat.class);
        willReturn(CHEAPEST_BUSINESS_PRICE).given(cheapestBusinessSeat).getPrice();
        willReturn(CHEAPEST_ECONOMIC_PRICE).given(cheapestEconomicSeat).getPrice();
        willReturn(NORMAL_BUSINESS_PRICE).given(normalBusinessSeat).getPrice();
        willReturn(NORMAL_ECONOMIC_PRICE).given(normalEconomicSeat).getPrice();
        seats = new ArrayList<>(Arrays.asList(cheapestBusinessSeat, normalBusinessSeat, cheapestEconomicSeat, normalEconomicSeat));
    }

    @Test
    public void givenAValidSeatsList_whenSelectingCheapest_shouldReturnCheapestFromAnyClass() {
        strategy = new CheapestSeatAssignationStrategy(Seat.seatClass.ANY);

        Seat takenSeat = strategy.assignSeat(seats);
        double takenSeatPrice = takenSeat.getPrice();

        assertEquals(CHEAPEST_ECONOMIC_PRICE, takenSeatPrice);
    }

    @Test
    public void givenAValidSeatsList_whenSelectingCheapestFromBusiness_shouldFindCorrespondingToSeatClass() {
        strategy = new CheapestSeatAssignationStrategy(Seat.seatClass.BUSINESS);
        willReturn(Seat.seatClass.BUSINESS).given(cheapestBusinessSeat).getPriceClass();
        willReturn(Seat.seatClass.BUSINESS).given(normalBusinessSeat).getPriceClass();
        willReturn(Seat.seatClass.ECONOMY).given(cheapestEconomicSeat).getPriceClass();
        willReturn(Seat.seatClass.ECONOMY).given(normalEconomicSeat).getPriceClass();

        Seat takenSeat = strategy.assignSeat(seats);
        double takenSeatPrice = takenSeat.getPrice();

        assertEquals(CHEAPEST_BUSINESS_PRICE, takenSeatPrice);
    }
}
