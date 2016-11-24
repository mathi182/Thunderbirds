package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.measurementsStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

public interface BaggageValidationStrategy {
    enum ValidationMode { ECONOMY }

    void validateBaggage(Baggage baggage);

}
