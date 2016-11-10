package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters;

import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.WeightConverterFactory.KILOGRAMS_FROM_REQUEST;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.WeightConverterFactory.POUND_FROM_REQUEST;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class WeightConverterFactoryTest {

    private WeightConverterFactory weightConverterFactory;

    @Before
    public void setup(){
        weightConverterFactory = new WeightConverterFactory();
    }

    @Test
    public void givenKilograms_whenGettingConverter_shouldReturnWeightConverterKilograms(){
        WeightConverter converter = weightConverterFactory.getConverter(KILOGRAMS_FROM_REQUEST);

        assertThat(converter, instanceOf(WeightConverterKilograms.class));
    }

    @Test
    public void givenPounds_whenGettingConverter_shouldReturnWeightConverterPounds(){
        WeightConverter converter = weightConverterFactory.getConverter(POUND_FROM_REQUEST);

        assertThat(converter, instanceOf(WeightConverterPound.class));
    }
}