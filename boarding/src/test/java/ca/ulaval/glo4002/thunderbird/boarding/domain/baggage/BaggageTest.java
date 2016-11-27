package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class BaggageTest {
    private static final UUID BAGGAGE_HASH = new UUID(2L, 2L);
    private static final int LINEAR_DIMENSION = 11;
    private static final int WEIGHT = 22;
    private static final String TYPE = "Type";

    private final Baggage baggage = new Baggage(BAGGAGE_HASH, LINEAR_DIMENSION, WEIGHT, TYPE);

    @Test
    public void shouldReturnRightValues() {
        assertEquals(BAGGAGE_HASH, baggage.getId());
        assertEquals(LINEAR_DIMENSION, baggage.getDimensionInMm());
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
        baggage.validate(LINEAR_DIMENSION, WEIGHT);
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void whenDimensionIsInvalid_shouldThrowAnException() {
        baggage.validate(LINEAR_DIMENSION - 1, WEIGHT);
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void whenWeightIsInvalid_shouldThrowAnException() {
        baggage.validate(LINEAR_DIMENSION, WEIGHT - 1);
    }
}