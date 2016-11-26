package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.Seat;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class SeatClassBaggageStrategyFactoryTest {
    private SeatClassBaggageStrategyFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new SeatClassBaggageStrategyFactory();
    }

    @Test
    public void whenGetttingSeatClassCheckedStrategy_shouldReturnEconomySeatClassBaggageStrategy() throws Exception {
        SeatClassBaggageTemplate strategy = factory.getStrategy(Seat.SeatClass.ECONOMY);

        assertThat(strategy, instanceOf(EconomySeatClassBaggageStrategy.class));
    }

    @Test
    public void whenGetttingSeatClassBusinessStrategy_shouldReturnBusinessSeatClassBaggageStrategy() throws Exception {
        SeatClassBaggageTemplate strategy = factory.getStrategy(Seat.SeatClass.BUSINESS);

        assertThat(strategy, instanceOf(BusinessSeatClassBaggageStrategy.class));
    }
}