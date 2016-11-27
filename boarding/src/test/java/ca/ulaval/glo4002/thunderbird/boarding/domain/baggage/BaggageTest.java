package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaggageTest {
    private static final UUID BAGGAGE_HASH = new UUID(2L, 2L);
    private static final int LINEAR_DIMENSION_VALUE = 11;
    private static final Length LINEAR_DIMENSION = Length.fromMillimeters(LINEAR_DIMENSION_VALUE);
    private static final int WEIGHT_VALUE = 22;
    private static final Mass WEIGHT = Mass.fromGrams(WEIGHT_VALUE);
    private static final String TYPE = "Type";

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
    public void givenAPrice_whenSetThisPrice_shouldBeSaved() {
        float expectedPrice = 5342;

        baggage.setPrice(expectedPrice);

        float actualPrice = baggage.getPrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void whenDimensionAndWeightAreValid_shouldNotThrowAnException() {
        baggage.validate(LINEAR_DIMENSION_VALUE, WEIGHT_VALUE);
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void whenDimensionIsInvalid_shouldThrowAnException() {
        baggage.validate(LINEAR_DIMENSION_VALUE - 1, WEIGHT_VALUE);
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void whenWeightIsInvalid_shouldThrowAnException() {
        baggage.validate(LINEAR_DIMENSION_VALUE, WEIGHT_VALUE - 1);
    }
}