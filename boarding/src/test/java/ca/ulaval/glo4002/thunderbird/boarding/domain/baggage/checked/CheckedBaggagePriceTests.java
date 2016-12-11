package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;


public class CheckedBaggagePriceTests {
    private static final int BAGGAGE_COST = 50;
    private static final float OVERWEIGHT_BAGGAGE_EXCESS_FEES = 1.1f;
    private static final float SPORT_BAGGAGE_EXCESS_FEES = 1.25f;
    private static final float DELTA = 0.01f;
    private static final String SPORT_TYPE = "sport";
    private static final String CHECKED_TYPE = "checked";
    private BaggageFactory baggageFactory = new BaggageFactory();

    private final Length validDimension = BusinessBaggage.MAX_LENGTH;
    //TODO Find a way to create an invalidDimension cleanly.
    private final Length invalidDimension = Length.fromCentimeters(BusinessBaggage.MAX_LENGTH.toCentimeters() + 1);
    private final Mass validWeight = BusinessBaggage.MAX_WEIGHT;
    //TODO Find a way to create an invalidWeight cleanly.
    private final Mass invalidWeight = Mass.fromGrams(BusinessBaggage.MAX_WEIGHT.toGrams() + 1);
    private Passenger passenger;
    private Baggage baggage;

    @Before
    public void setUp(){
        passenger = mock(Passenger.class);
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();
    }

    @Test
    public void givenValidDimensionAndWeight_whenCalculatingPrice_shouldNotPayOverweightExcessFees() {
        NormalizedBaggageDTO withinLimitBaggage = new NormalizedBaggageDTO(validDimension, validWeight,CHECKED_TYPE);
        baggage = baggageFactory.createBaggage(passenger, withinLimitBaggage);

        float actualPrice = baggage.getBasePrice();

        assertEquals(BAGGAGE_COST, actualPrice, DELTA);
    }

    @Test
    public void givenOversizeDimension_whenCalculatingPrice_shouldPayOverweightExcessFees() {
        NormalizedBaggageDTO excessLength = new NormalizedBaggageDTO(invalidDimension, validWeight,CHECKED_TYPE);
        baggage = baggageFactory.createBaggage(passenger, excessLength);

        float actualPrice = baggage.getBasePrice();

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenInvalidWeight_whenCalculatingPrice_shouldPayOverweightExcessFees() {
        NormalizedBaggageDTO excessWeight = new NormalizedBaggageDTO(validDimension,invalidWeight,CHECKED_TYPE);
        baggage = baggageFactory.createBaggage(passenger, excessWeight);

        float actualPrice = baggage.getBasePrice();

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenInvalidDimensionAndWeight_whenCalculatingPrice_shouldPayOverweightExcessFeesTwoTimes() {
        NormalizedBaggageDTO excessWnL = new NormalizedBaggageDTO(invalidDimension,invalidWeight,CHECKED_TYPE);

        baggage = baggageFactory.createBaggage(passenger, excessWnL);

        float actualPrice = baggage.getBasePrice();

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithValidDimensionAndWeight_whenCalculatingPrice_shouldOnlyPaySportExcessFees() {
        NormalizedBaggageDTO sportWithinLimit = new NormalizedBaggageDTO(validDimension,validWeight,SPORT_TYPE);
        baggage = baggageFactory.createBaggage(passenger, sportWithinLimit);

        float actualPrice = baggage.getBasePrice();

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithInvalidDimension_whenCalculatingPrice_shouldOnlyPaySportExcessFees() {
        NormalizedBaggageDTO sportWithinLimit = new NormalizedBaggageDTO(invalidDimension,validWeight,SPORT_TYPE);
        baggage = baggageFactory.createBaggage(passenger, sportWithinLimit);

        float actualPrice = baggage.getBasePrice();

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithInvalidWeight_whenCalculatingPrice_shouldPaySportAndOverweightExcessFees() {
        NormalizedBaggageDTO sportWithinLimit = new NormalizedBaggageDTO(validDimension,invalidWeight,SPORT_TYPE);
        baggage = baggageFactory.createBaggage(passenger, sportWithinLimit);

        float actualPrice = baggage.getBasePrice();

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }
}
