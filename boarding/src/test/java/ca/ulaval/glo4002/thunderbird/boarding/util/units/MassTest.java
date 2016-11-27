package ca.ulaval.glo4002.thunderbird.boarding.util.units;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MassTest {

    private static final double DELTA = 0.001;
    private static final double MASS_IN_POUNDS = 1;
    private static final double MASS_IN_GRAMS = 453.592;
    private static final double MASS_IN_KILOGRAMS = 0.453592;

    private Mass mass;
    private double actualMass;

    @Test
    public void whenConvertingPoundsToGrams_shouldReturnConvertedValue() {
        mass = Mass.fromPounds(MASS_IN_POUNDS);
        actualMass = mass.toGrams();

        assertEquals(MASS_IN_GRAMS, actualMass, DELTA);
    }

    @Test
    public void whenConvertingGramsToGrams_shouldReturnSameValue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        actualMass = mass.toGrams();

        assertEquals(MASS_IN_GRAMS, actualMass, DELTA);
    }

    @Test
    public void whenConvertingKiloGramsToGrams_shouldReturnConvertedValue() {
        mass = Mass.fromKilograms(MASS_IN_KILOGRAMS);
        actualMass = mass.toGrams();

        assertEquals(MASS_IN_GRAMS, actualMass, DELTA);
    }

    @Test
    public void whenConvertingGramsToPounds_shouldReturnConvertedValue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        actualMass = mass.toPounds();

        assertEquals(MASS_IN_POUNDS, actualMass, DELTA);
    }

    @Test
    public void whenConvertingGramsToKiloGrams_shouldReturnConvertedValue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        actualMass = mass.toKilograms();

        assertEquals(MASS_IN_KILOGRAMS, actualMass, DELTA);
    }
}
