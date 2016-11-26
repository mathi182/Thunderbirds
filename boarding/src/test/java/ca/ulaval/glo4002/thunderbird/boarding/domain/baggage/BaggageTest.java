package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaggageTest {
    private static final int LINEAR_DIMENSION_IN_MM = 10;
    private static final int WEIGHT_IN_G = 10;
    private static final int DIFFERENT_WEIGHT_IN_G = 11;
    private static final int DIFFERENT_LINEAR_DIMENSION_IN_MM = 11;
    private static final String BAGGAGE_TYPE = "checked";
    private static final String DIFFERENT_BAGGAGE_TYPE = "invalid";
    public static final int WEIGHT_LIMIT = 11;
    public static final int WEIGHT_LIMIT_TOO_LOW = 10;
    public static final int DIMENSION_LIMIT = 10;
    public static final int DIMENSION_LIMIT_TOO_LOW = 9;

    private Baggage baggage;

    @Before
    public void setUp() throws Exception {
        baggage = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, BAGGAGE_TYPE);
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void givenInvalidDimensionInMmCheckedBaggage_whenValidating_shouldThrowBaggageDimensionInvalidException() {
        baggage.validate(DIMENSION_LIMIT_TOO_LOW, WEIGHT_LIMIT);
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void givenInvalidWeightInGCheckedBaggage_whenValidating_shouldThrowBaggageWeightInvalidException() {
        baggage.validate(DIMENSION_LIMIT, WEIGHT_LIMIT_TOO_LOW);
    }

    @Test
    public void givenValidCheckedBaggage_whenValidating_shouldDoNothing() {
        baggage.validate(DIMENSION_LIMIT, WEIGHT_LIMIT);
    }

    @Test
    public void givenNull_whenEquals_shouldReturnFalse() throws Exception {
        boolean resultActual = baggage.equals(null);

        assertFalse(resultActual);
    }

    @Test
    public void givenSameObject_whenEquals_shouldReturnTrue() throws Exception {
        Baggage baggage2 = baggage;

        boolean resultActual = baggage.equals(baggage2);
        assertTrue(resultActual);
    }

    @Test
    public void givenAnotherObject_whenEquals_shouldReturnFalse() {
        Object object = new Object();

        boolean resultActual = baggage.equals(object);
        assertFalse(resultActual);

    }

    @Test
    public void givenBaggagesWithDifferentType_whenEquals_shouldReturnFalse() throws Exception {
        Baggage baggage2 = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, DIFFERENT_BAGGAGE_TYPE);

        boolean resultActual = baggage.equals(baggage2);

        assertFalse(resultActual);
    }

    @Test
    public void givenBaggageWithDifferentWeight_whenEquals_shouldReturnFalse() throws Exception {
        Baggage baggage2 = new Baggage(LINEAR_DIMENSION_IN_MM, DIFFERENT_WEIGHT_IN_G, BAGGAGE_TYPE);

        boolean resultActual = baggage.equals(baggage2);

        assertFalse(resultActual);
    }

    @Test
    public void givenBaggageWithDifferentDimension_whenEquals_shouldReturnFalse() throws Exception {
        Baggage baggage2 = new Baggage(DIFFERENT_LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, BAGGAGE_TYPE);

        boolean resultActual = baggage.equals(baggage2);

        assertFalse(resultActual);
    }
}