package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.baggageStrategies;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy.BaggagePriceStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy.CheckedBaggagePriceStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.CheckedBaggageValidationStrategy;

public class CheckedBaggageStrategies extends BaggageStrategies {
    @Override
    public BaggagePriceStrategy createPriceStrategy() {
        return new CheckedBaggagePriceStrategy();
    }

    @Override
    public BaggageValidationStrategy createValidationStrategy() {
        return new CheckedBaggageValidationStrategy();
    }
}
