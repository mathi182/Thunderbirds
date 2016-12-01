package ca.ulaval.glo4002.thunderbird.boarding.util.units;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LengthTest {

    private static final double DELTA = 0.001;
    private static final double LENGTH_IN_MILLIMETERS = 25.4;
    private static final double LENGTH_IN_INCHES = 1;
    private static final double LENGTH_IN_CENTIMETERS = 2.54;
    private Length length;
    private double actualLength;

    @Test
    public void whenConvertingCentimetersToMillimeters_shouldReturnTheConvertedValue() {
        length = Length.fromCentimeters(LENGTH_IN_CENTIMETERS);
        actualLength = length.toMillimeters();

        assertEquals(LENGTH_IN_MILLIMETERS, actualLength, DELTA);
    }

    @Test
    public void whenConvertingInchesToMillimeters_shouldReturnTheConvertedValue() {
        length = Length.fromInches(LENGTH_IN_INCHES);
        actualLength = length.toMillimeters();

        assertEquals(LENGTH_IN_MILLIMETERS, actualLength, DELTA);
    }

    @Test
    public void whenConvertingMillimetersToMillimeter_shouldReturnTheSameValue() {
        length = Length.fromMillimeters(LENGTH_IN_MILLIMETERS);
        actualLength = length.toMillimeters();

        assertEquals(LENGTH_IN_MILLIMETERS, actualLength, DELTA);
    }

    @Test
    public void whenConvertingMillimetersToCentimeters_shouldReturnConvertedValue() {
        length = Length.fromMillimeters(LENGTH_IN_MILLIMETERS);
        actualLength = length.toCentimeters();

        assertEquals(LENGTH_IN_CENTIMETERS, actualLength, DELTA);
    }

    @Test
    public void whenConvertingMillimeterToInches_shouldReturnConvertedValue() {
        length = Length.fromMillimeters(LENGTH_IN_MILLIMETERS);
        actualLength = length.toInches();

        assertEquals(LENGTH_IN_INCHES, actualLength, DELTA);
    }

    @Test
    public void whenComparingWithLengthWithSameMillimeters_shouldReturnTrue() {
        length = Length.fromMillimeters(LENGTH_IN_MILLIMETERS);
        Length secondLength = Length.fromMillimeters(LENGTH_IN_MILLIMETERS);
        boolean areEquals = length.equals(secondLength);

        assertTrue(areEquals);
    }

    @Test
    public void whenComparingWithOtherObject_shouldReturnFalse() {
        length = Length.fromMillimeters(LENGTH_IN_MILLIMETERS);
        Object obj = new Object();
        boolean areEquals = length.equals(obj);

        assertFalse(areEquals);
    }

}