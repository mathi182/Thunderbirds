package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class BaggageTest {
    private static final UUID BAGGAGE_HASH = new UUID(2L, 2L);
    private static final String TYPE = "Type";

    private static final int DIMENSION_VALUE = 11;
    private static final Length LINEAR_DIMENSION = Length.fromMillimeters(DIMENSION_VALUE);
    private static final Length INVALID_DIMENSION = Length.fromMillimeters(DIMENSION_VALUE - 1);

    private static final int WEIGHT_VALUE = 22;
    private static final Mass WEIGHT = Mass.fromGrams(WEIGHT_VALUE);
    private static final Mass INVALID_WEIGHT = Mass.fromGrams(WEIGHT_VALUE - 1);

    private final Baggage baggage = new Baggage(BAGGAGE_HASH, LINEAR_DIMENSION, WEIGHT, TYPE);

    @Test
    public void shouldReturnRightValues() {
        assertEquals(BAGGAGE_HASH, baggage.getId());
        assertEquals(LINEAR_DIMENSION, baggage.getDimension());
        assertEquals(WEIGHT, baggage.getWeight());
        assertEquals(TYPE, baggage.getType());
        assertEquals(0, baggage.getPrice(), 0.0f);
    }

    @Test
    public void givenAPrice_whenSettingThisPrice_shouldBeSaved() {
        float expectedPrice = 5342;

        baggage.setPrice(expectedPrice);

        float actualPrice = baggage.getPrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void whenDimensionAndWeightAreValid_shouldNotThrowAnException() {
        baggage.validate(LINEAR_DIMENSION, WEIGHT);
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void whenDimensionIsInvalid_shouldThrowAnException() {
        baggage.validate(INVALID_DIMENSION, WEIGHT);
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void whenWeightIsInvalid_shouldThrowAnException() {
        baggage.validate(LINEAR_DIMENSION, INVALID_WEIGHT);
    }
}