package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.unitConverters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DimensionConverterInchesTest {
    public static final int A_DIMENSION_IN_INCHES = 2;
    public static final int SAME_DIMENSION_IN_MILLIMETERS = 50;

    private DimensionConverterInches dimensionConverterInches;

    @Before
    public void setUp() throws Exception {
        dimensionConverterInches = new DimensionConverterInches();
    }

    @Test
    public void given0Inches_whenConverting_shouldConvertTo0Mm() {
        int actual = dimensionConverterInches.convertToMillimeters(0);

        assertEquals(0, actual);
    }

    @Test
    public void givenADimension_whenConverting_shouldConvertToMm() {
        int actual = dimensionConverterInches.convertToMillimeters(A_DIMENSION_IN_INCHES);

        assertEquals(SAME_DIMENSION_IN_MILLIMETERS, actual);
    }
}