package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy.BaggagePriceCalculatorDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckedBaggagePriceStrategyTest {
    public static final int NUMBER_OF_BAGGAGES_OF_SAME_TYPE = 0;
    public static final int COSTING_CHECKED_BAGGAGE_COST = 1;
    public static final int FREE_CHECKED_BAGGAGE = 1;
    public static final int NO_FREE_CHECKED_BAGGAGE = 0;
    private BaggagePriceCalculatorDTO priceCalculatorDTO;

    @Before
    public void setUp() throws Exception {
        priceCalculatorDTO = new BaggagePriceCalculatorDTO();
        priceCalculatorDTO.numberOfBaggagesOfSameType = NUMBER_OF_BAGGAGES_OF_SAME_TYPE;
        priceCalculatorDTO.costingCheckedBaggageCost = COSTING_CHECKED_BAGGAGE_COST;
    }

    @Test
    public void givenOneFreeCheckedBaggage_whenCalculating_shouldReturn0() throws Exception {
        priceCalculatorDTO.freeCheckedBaggageCount = FREE_CHECKED_BAGGAGE;

        CheckedBaggagePriceStrategy checkedBaggagePriceStrategy = new CheckedBaggagePriceStrategy();
        float actualPrice = checkedBaggagePriceStrategy.calculatePrice(priceCalculatorDTO);

        assertEquals(0, actualPrice, 0.01);
    }

    @Test
    public void givenNoFreeCheckedBaggage_whenCalculating_shouldReturnCostingCheckedBaggagePrice() throws Exception {
        priceCalculatorDTO.costingCheckedBaggageCost = COSTING_CHECKED_BAGGAGE_COST;
        priceCalculatorDTO.freeCheckedBaggageCount = NO_FREE_CHECKED_BAGGAGE;

        CheckedBaggagePriceStrategy checkedBaggagePriceStrategy = new CheckedBaggagePriceStrategy();
        float actualPrice = checkedBaggagePriceStrategy.calculatePrice(priceCalculatorDTO);

        assertEquals(COSTING_CHECKED_BAGGAGE_COST, actualPrice, 0.01);
    }
}