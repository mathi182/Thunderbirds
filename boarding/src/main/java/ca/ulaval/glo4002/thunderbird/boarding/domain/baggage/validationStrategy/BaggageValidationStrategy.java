package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

public interface BaggageValidationStrategy {
    enum ValidationMode { ECONOMY }

    void validateBaggage(Baggage baggage);

}
