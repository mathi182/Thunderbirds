package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import org.junit.Test;

import static org.junit.Assert.*;

public class BaggageTest {
    private static final int LINEAR_DIMENSION_IN_MM = 10;
    private static final int WEIGHT_IN_G = 10;
    private static final int DIFFERENT_WEIGHT_IN_G = 11;
    private static final int DIFFERENT_LINEAR_DIMENSION_IN_MM = 11;
    private static final String BAGGAGE_TYPE = "checked";
    private static final String DIFFERENT_BAGGAGE_TYPE = "invalid";

    @Test
    public void givenNull_whenEquals_shouldReturnFalse() throws Exception {
        Baggage baggage = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, "checked");

        boolean resultActual = baggage.equals(null);

        assertFalse(resultActual);
    }

    @Test
    public void givenSameObject_whenEquals_shouldReturnTrue() throws Exception {
        Baggage baggage1 = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, BAGGAGE_TYPE);
        Baggage baggage2 = baggage1;

        boolean resultActual = baggage1.equals(baggage2);
        assertTrue(resultActual);
    }

    @Test
    public void givenAnotherObject_whenEquals_shouldReturnFalse() {
        Baggage baggage1 = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, BAGGAGE_TYPE);
        Object object = new Object();

        boolean resultActual = baggage1.equals(object);
        assertFalse(resultActual);

    }

    @Test
    public void givenBaggagesWithDifferentType_whenEquals_shouldReturnFalse() throws Exception {
        Baggage baggage1 = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, BAGGAGE_TYPE);
        Baggage baggage2 = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, DIFFERENT_BAGGAGE_TYPE);

        boolean resultActual = baggage1.equals(baggage2);

        assertFalse(resultActual);
    }

    @Test
    public void givenBaggageWithDifferentWeight_whenEquals_shouldReturnFalse() throws Exception {
        Baggage baggage1 = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, BAGGAGE_TYPE);
        Baggage baggage2 = new Baggage(LINEAR_DIMENSION_IN_MM, DIFFERENT_WEIGHT_IN_G, BAGGAGE_TYPE);

        boolean resultActual = baggage1.equals(baggage2);

        assertFalse(resultActual);
    }

    @Test
    public void givenBaggageWithDifferentDimension_whenEquals_shouldReturnFalse() throws Exception {
        Baggage baggage1 = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, BAGGAGE_TYPE);
        Baggage baggage2 = new Baggage(DIFFERENT_LINEAR_DIMENSION_IN_MM, WEIGHT_IN_G, BAGGAGE_TYPE);

        boolean resultActual = baggage1.equals(baggage2);

        assertFalse(resultActual);
    }
}