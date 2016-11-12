package ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

public class LuggageValidationStrategyFactory {
    public LuggageValidationStrategy getStrategy(LuggageValidationStrategy.ValidationMode mode) {
        switch (mode) {
            case ECONOMY:
                return new EconomyCheckedLuggageValidationStrategy();
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}