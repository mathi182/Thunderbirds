package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.unitConverters;

public class WeightConverterKilograms implements WeightConverter {
    public static final int GRAMS_IN_KILOGRAMS = 1000;

    @Override
    public int convertToGrams(double weight) {
        return (int) (weight * GRAMS_IN_KILOGRAMS);
    }
}
