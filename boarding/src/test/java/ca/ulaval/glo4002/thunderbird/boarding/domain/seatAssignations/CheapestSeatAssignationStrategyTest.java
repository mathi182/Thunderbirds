package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class CheapestSeatAssignationStrategyTest {

    private CheapestSeatAssignationStrategy strategy;
    private List<Seat> seats;
    private Seat cheapestBusinessSeat;
    private Seat cheapestEconomicSeat;
    private Seat normalBusinessSeat;
    private Seat normalEconomicSeat;
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
        givenAValidSeatsList();
    }

    private void givenAValidSeatsList() {
        willReturn(CHEAPEST_BUSINESS_PRICE).given(cheapestBusinessSeat).getPrice();
        willReturn(CHEAPEST_ECONOMIC_PRICE).given(cheapestEconomicSeat).getPrice();
        willReturn(NORMAL_BUSINESS_PRICE).given(normalBusinessSeat).getPrice();
        willReturn(NORMAL_ECONOMIC_PRICE).given(normalEconomicSeat).getPrice();
        willReturn(Seat.SeatClass.BUSINESS).given(cheapestBusinessSeat).getSeatClass();
        willReturn(Seat.SeatClass.BUSINESS).given(normalBusinessSeat).getSeatClass();
        willReturn(Seat.SeatClass.ECONOMY).given(cheapestEconomicSeat).getSeatClass();
        willReturn(Seat.SeatClass.ECONOMY).given(normalEconomicSeat).getSeatClass();
        seats = new ArrayList<>(Arrays.asList(cheapestBusinessSeat, normalBusinessSeat, cheapestEconomicSeat, normalEconomicSeat));
    }

    @Test
    public void givenAValidSeatsList_whenSelectingCheapest_shouldReturnCheapestFromAnyClass() {
        willReturn(true).given(cheapestEconomicSeat).hasLowerPriceThan(any(Seat.class));
        strategy = new CheapestSeatAssignationStrategy(Seat.SeatClass.ANY);

        Seat takenSeat = strategy.assignSeat(seats);
        double takenSeatPrice = takenSeat.getPrice();

        assertEquals(CHEAPEST_ECONOMIC_PRICE, takenSeatPrice);
    }

    @Test
    public void givenAValidSeatsList_whenSelectingCheapestFromBusiness_shouldFindCorrespondingToSeatClass() {
        willReturn(true).given(cheapestBusinessSeat).hasLowerPriceThan(any(Seat.class));
        strategy = new CheapestSeatAssignationStrategy(Seat.SeatClass.BUSINESS);

        Seat takenSeat = strategy.assignSeat(seats);
        double takenSeatPrice = takenSeat.getPrice();

        assertEquals(CHEAPEST_BUSINESS_PRICE, takenSeatPrice);
    }

    @Test (expected = NoMoreSeatAvailableException.class)
    public void givenAValidSeatsListWithoutBusiness_whenSelectingCheapestSeatFromBusiness_shouldThrowNoMoreSeatAvailable() {
        List<Seat> seatsWithoutBusiness = new ArrayList<>(Arrays.asList(cheapestEconomicSeat, normalEconomicSeat));
        strategy = new CheapestSeatAssignationStrategy(Seat.SeatClass.BUSINESS);

        strategy.assignSeat(seatsWithoutBusiness);
    }
}
