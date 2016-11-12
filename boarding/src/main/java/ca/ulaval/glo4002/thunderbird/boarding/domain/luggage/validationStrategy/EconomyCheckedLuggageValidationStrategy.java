package ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.exceptions.LuggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.exceptions.LuggageWeightInvalidException;

public class EconomyCheckedLuggageValidationStrategy implements LuggageValidationStrategy {
    public static final int LIMIT_IN_GRAMS = 23000;
    public static final int LIMIT_IN_MM = 1580;

    @Override
    public void validateLuggage(Luggage luggage) {
        if (luggage.getWeightInGrams() > LIMIT_IN_GRAMS) {
            throw new LuggageWeightInvalidException();
        } else if (luggage.getDimensionInMm() > LIMIT_IN_MM) {
            throw new LuggageDimensionInvalidException();
        }
    }
}