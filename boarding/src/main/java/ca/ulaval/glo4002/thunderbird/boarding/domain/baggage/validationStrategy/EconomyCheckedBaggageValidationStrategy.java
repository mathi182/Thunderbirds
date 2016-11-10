package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategy;

public class EconomyCheckedBaggageValidationStrategy implements BaggageValidationStrategy {
    public static final int LIMIT_IN_GRAMS = 23000;
    public static final int LIMIT_IN_MM = 1580;

    public EconomyCheckedBaggageValidationStrategy() {
    }

    @Override
    public void validateBaggage(Baggage baggage) {
        if (baggage.getWeightInGrams() > LIMIT_IN_GRAMS) {
            throw new BaggageWeightInvalidException();
        } else if (baggage.getDimensionInMm() > LIMIT_IN_MM) {
            throw new BaggageDimensionInvalidException();
        }
    }
}
