package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters;

public class WeightConverterPound implements WeightConverter {
    private static final double GRAMS_IN_POUND = 453.592;

    @Override
    public int convertToGrams(double weight) {
        return (int)(weight * GRAMS_IN_POUND);
    }
}
