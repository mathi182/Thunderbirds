package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.measurementsStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;

public class EconomyCheckedBaggageValidationStrategy implements BaggageValidationStrategy {
    public static final int LIMIT_IN_GRAMS = 23000;
    public static final int LIMIT_IN_MM = 1580;

    @Override
    public void validateBaggage(Baggage baggage) {
        if (baggage.getWeightInGrams() > LIMIT_IN_GRAMS) {
            throw new BaggageWeightInvalidException();
        } else if (baggage.getDimensionInMm() > LIMIT_IN_MM) {
            throw new BaggageDimensionInvalidException();
        }
    }
}