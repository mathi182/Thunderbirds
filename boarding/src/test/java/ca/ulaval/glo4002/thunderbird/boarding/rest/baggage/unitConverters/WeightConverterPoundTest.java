package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeightConverterPoundTest {
    private static final double A_WEIGHT_IN_POUND = 23;
    private static final int THE_SAME_WEIGHT_IN_GRAMS = 10432;
    private WeightConverterPound converterPound;

    @Before
    public void setup() {
        converterPound = new WeightConverterPound();
    }

    @Test
    public void givenZeroPound_whenConverting_shouldReturn0Gram() {
        int actual = converterPound.convertToGrams(0);

        assertEquals(0, actual);
    }

    @Test
    public void givenAWeightInPounds_whenConverting_shouldReturnTheSameWeightInGrams() {
        int actual = converterPound.convertToGrams(A_WEIGHT_IN_POUND);

        assertEquals(THE_SAME_WEIGHT_IN_GRAMS, actual);
    }
}