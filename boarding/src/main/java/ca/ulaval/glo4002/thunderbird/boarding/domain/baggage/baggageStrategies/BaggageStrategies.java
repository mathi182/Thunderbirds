package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.baggageStrategies;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy.BaggagePriceStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageType.CHECKED;

public abstract class BaggageStrategies {
    private static final CheckedBaggageStrategies CHECKED_BAGGAGE_TOOLKIT = new CheckedBaggageStrategies();

    public static final BaggageStrategies getFactory(String type) {
        switch (type) {
            case CHECKED:
                return CHECKED_BAGGAGE_TOOLKIT;
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }

    public abstract BaggagePriceStrategy createPriceStrategy();

    public abstract BaggageValidationStrategy createValidationStrategy();
}
