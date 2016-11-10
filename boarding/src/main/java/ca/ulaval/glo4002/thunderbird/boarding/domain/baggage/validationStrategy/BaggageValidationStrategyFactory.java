package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

public class BaggageValidationStrategyFactory {
    public BaggageValidationStrategy getStrategy(BaggageValidationStrategy.ValidationMode mode) {
        switch (mode) {
            case ECONOMY:
                return new EconomyCheckedBaggageValidationStrategy();
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}
