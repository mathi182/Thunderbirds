package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Sport;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CheckedBaggagePriceTests {
    private static final int BAGGAGE_COST = 50;
    private static final float OVERWEIGHT_BAGGAGE_EXCESS_FEES = 1.1f;
    private static final float SPORT_BAGGAGE_EXCESS_FEES = 1.25f;
    private static final Speciality SPORT_SPECIALITY = new Sport();
    private static final Speciality CLASS_SPECIALITY = new Classic();
    private static final float DELTA = 0.01f;

    private final Length dimensionLimit = Length.fromMillimeters(12);
    private final Length invalidDimension = Length.fromMillimeters(13);
    private final Mass weightLimit = Mass.fromGrams(34);
    private final Mass invalidWeight = Mass.fromGrams(35);

    @Test
    public void givenValidDimensionAndWeight_whenCalculatingPrice_shouldNotPayOverweightExcessFees() {
        Baggage baggage = new CheckedBaggage(dimensionLimit, weightLimit, CLASS_SPECIALITY);

        float actualPrice = baggage.getBasePrice(dimensionLimit, weightLimit);

        assertEquals(BAGGAGE_COST, actualPrice, DELTA);
    }

    @Test
    public void givenInvalidDimension_whenCalculatingPrice_shouldPayOverweightExcessFees() {
        Baggage baggage = new CheckedBaggage(invalidDimension, weightLimit, CLASS_SPECIALITY);

        float actualPrice = baggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenInvalidWeight_whenCalculatingPrice_shouldPayOverweightExcessFees() {
        Baggage validBaggage = new CheckedBaggage(dimensionLimit, invalidWeight, CLASS_SPECIALITY);

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenInvalidDimensionAndWeight_whenCalculatingPrice_shouldPayOverweightExcessFeesTwoTimes() {
        Baggage validBaggage = new CheckedBaggage(invalidDimension, invalidWeight, CLASS_SPECIALITY);

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithValidDimensionAndWeight_whenCalculatingPrice_shouldOnlyPaySportExcessFees() {
        Baggage validBaggage = new CheckedBaggage(dimensionLimit, weightLimit, SPORT_SPECIALITY);

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithInvalidDimension_whenCalculatingPrice_shouldOnlyPaySportExcessFees() {
        Baggage validBaggage = new CheckedBaggage(invalidDimension, weightLimit, SPORT_SPECIALITY);

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithInvalidWeight_whenCalculatingPrice_shouldPaySportAndOverweightExcessFees() {
        Baggage validBaggage = new CheckedBaggage(dimensionLimit, invalidWeight, SPORT_SPECIALITY);

        float actualPrice = validBaggage.getBasePrice(dimensionLimit, weightLimit);

        float expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }
}
