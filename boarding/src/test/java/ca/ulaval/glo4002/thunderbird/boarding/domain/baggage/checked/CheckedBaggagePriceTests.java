package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CheckedBaggagePriceTests {
    private static final int BAGGAGE_COST = 50;
    private static final float OVERWEIGHT_BAGGAGE_EXCESS_FEES = 1.1f;
    private static final float SPORT_BAGGAGE_EXCESS_FEES = 1.25f;
    private static final String SPORT_BAGGAGE = "sport";

    private Length dimensionLimit;
    private Mass weightLimit;
    private Length invalidDimension;
    private Mass invalidWeight;

    @Before
    public void setUp() {
        double validDimensionInMm = 12;
        double validWeightInGram = 34;
        dimensionLimit = Length.fromMillimeters(validDimensionInMm);
        weightLimit = Mass.fromGrams(validWeightInGram);

        double invalidDimensionInMm = validDimensionInMm + 1;
        double invalidWeightInGram = validWeightInGram + 1;
        invalidDimension = Length.fromMillimeters(invalidDimensionInMm);
        invalidWeight = Mass.fromGrams(invalidWeightInGram);
    }

    @Test
    public void givenValidDimensionAndWeight_whenCalculatingPrice_shouldNotPayOverweightExcessFees() {
        Baggage baggage = new CheckedBaggage(dimensionLimit, weightLimit, "TYPE");

        float actualPrice = baggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST;
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenInvalidDimension_whenCalculatingPrice_shouldPayOverweightExcessFees() {
        Baggage baggage = new CheckedBaggage(invalidDimension, weightLimit, "TYPE");

        float actualPrice = baggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenInvalidWeight_whenCalculatingPrice_shouldPayOverweightExcessFees() {
        Baggage validBaggage = new CheckedBaggage(dimensionLimit, invalidWeight, "TYPE");

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenInvalidDimensionAndWeight_whenCalculatingPrice_shouldPayOverweightExcessFeesTwoTimes() {
        Baggage validBaggage = new CheckedBaggage(invalidDimension, invalidWeight, "TYPE");

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenSportBaggageWithValidDimensionAndWeight_whenCalculatingPrice_shouldOnlyPaySportExcessFees() {
        Baggage validBaggage = new CheckedBaggage(dimensionLimit, weightLimit, SPORT_BAGGAGE);

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenSportBaggageWithInvalidDimension_whenCalculatingPrice_shouldOnlyPaySportExcessFees() {
        Baggage validBaggage = new CheckedBaggage(invalidDimension, weightLimit, SPORT_BAGGAGE);

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenSportBaggageWithInvalidWeight_whenCalculatingPrice_shouldPaySportAndOverweightExcessFees() {
        Baggage validBaggage = new CheckedBaggage(dimensionLimit, invalidWeight, SPORT_BAGGAGE);

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }
}
