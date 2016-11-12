package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.unitConverters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DimensionConverterCentimetersTest {
    public static final int A_DIMENSION_IN_CENTIMETERS = 1;
    public static final int SAME_DIMENSION_IN_MILLIMETERS = 10;

    private DimensionConverterCentimeters dimensionConverterCentimeters;

    @Before
    public void setUp() throws Exception {
        dimensionConverterCentimeters = new DimensionConverterCentimeters();
    }

    @Test
    public void given0Cm_whenConverting_shouldConvertTo0Mm() {
        int actual = dimensionConverterCentimeters.convertToMillimeters(0);

        assertEquals(0, actual);
    }

    @Test
    public void givenADimension_whenConverting_shouldConvertToMm() {
        int actual = dimensionConverterCentimeters.convertToMillimeters(A_DIMENSION_IN_CENTIMETERS);

        assertEquals(SAME_DIMENSION_IN_MILLIMETERS, actual);
    }
}