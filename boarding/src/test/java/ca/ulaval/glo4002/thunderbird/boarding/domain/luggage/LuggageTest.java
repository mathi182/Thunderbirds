package ca.ulaval.glo4002.thunderbird.boarding.domain.luggage;

import org.junit.Test;

import static org.junit.Assert.*;

public class LuggageTest {
    private static final int LINEAR_DIMENSION_IN_MM = 10;
    private static final int WEIGHT_IN_G = 10;
    private static final int DIFFERENT_WEIGHT_IN_G = 11;
    private static final int DIFFERENT_LINEAR_DIMENSION_IN_MM = 11;
    private static final String LUGGAGE_TYPE = "checked";
    private static final String DIFFERENT_LUGGAGE_TYPE = "invalid";

    @Test
    public void givenNull_whenEquals_shouldReturnFalse() throws Exception {
        Luggage luggage = new Luggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, "checked");

        boolean resultActual = luggage.equals(null);

        assertFalse(resultActual);
    }

    @Test
    public void givenSameObject_whenEquals_shouldReturnTrue() throws Exception {
        Luggage luggage1 = new Luggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, LUGGAGE_TYPE);
        Luggage luggage2 = luggage1;

        boolean resultActual = luggage1.equals(luggage2);
        assertTrue(resultActual);
    }

    @Test
    public void givenAnotherObject_whenEquals_shouldReturnFalse() {
        Luggage luggage1 = new Luggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, LUGGAGE_TYPE);
        Object object = new Object();

        boolean resultActual = luggage1.equals(object);
        assertFalse(resultActual);

    }

    @Test
    public void givenLuggagesWithDifferentType_whenEquals_shouldReturnFalse() throws Exception {
        Luggage luggage1 = new Luggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, LUGGAGE_TYPE);
        Luggage luggage2 = new Luggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, DIFFERENT_LUGGAGE_TYPE);

        boolean resultActual = luggage1.equals(luggage2);

        assertFalse(resultActual);
    }

    @Test
    public void givenLuggageWithDifferentWeight_whenEquals_shouldReturnFalse() throws Exception {
        Luggage luggage1 = new Luggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, LUGGAGE_TYPE);
        Luggage luggage2 = new Luggage(LINEAR_DIMENSION_IN_MM, DIFFERENT_WEIGHT_IN_G, LUGGAGE_TYPE);

        boolean resultActual = luggage1.equals(luggage2);

        assertFalse(resultActual);
    }

    @Test
    public void givenLuggageWithDifferentDimension_whenEquals_shouldReturnFalse() throws Exception {
        Luggage luggage1 = new Luggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, LUGGAGE_TYPE);
        Luggage luggage2 = new Luggage(DIFFERENT_LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, LUGGAGE_TYPE);

        boolean resultActual = luggage1.equals(luggage2);

        assertFalse(resultActual);
    }
}