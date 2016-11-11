package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters;

import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters.DimensionConverterFactory.CENTIMER_UNIT_FROM_REQUEST;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters.DimensionConverterFactory.INCH_UNIT_FROM_REQUEST;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class DimensionConverterFactoryTest {
    DimensionConverterFactory dimensionConverterFactory;

    @Before
    public void setUp() throws Exception {
        dimensionConverterFactory = new DimensionConverterFactory();
    }

    @Test
    public void givenCentimeters_whenGettingStrategy_shouldReturnDimensionConverterCentimeters() {
        DimensionConverter dimensionConverter = dimensionConverterFactory.getConverter(CENTIMER_UNIT_FROM_REQUEST);

        assertThat(dimensionConverter, instanceOf(DimensionConverterCentimeters.class));
    }

    @Test
    public void givenInches_whenGettingStrategy_shouldReturnDimensionConverterInches() {
        DimensionConverter dimensionConverter = dimensionConverterFactory.getConverter(INCH_UNIT_FROM_REQUEST);

        assertThat(dimensionConverter, instanceOf(DimensionConverterInches.class));
    }
}