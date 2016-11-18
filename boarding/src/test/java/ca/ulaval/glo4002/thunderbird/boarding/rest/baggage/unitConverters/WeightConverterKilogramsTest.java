package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeightConverterKilogramsTest {
    public static final int SAME_WEIGHT_IN_GRAMS = 1000;
    public static final int SAME_WEIGHT_IN_KGS = 1;
    private WeightConverter weightConverter;

    @Before
    public void setUp() throws Exception {
        weightConverter = new WeightConverterKilograms();
    }

    @Test
    public void given0Kilogram_whenConverting_shouldReturn0Gram() {
        int actual = weightConverter.convertToGrams(0);

        assertEquals(0, actual);
    }

    @Test
    public void givenWeightInKgs_whenConverting_shouldConvertToGrams() throws Exception {
        int actual = weightConverter.convertToGrams(SAME_WEIGHT_IN_KGS);

        assertEquals(SAME_WEIGHT_IN_GRAMS, actual);
    }
}