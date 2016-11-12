package ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;

public interface LuggageValidationStrategy {
    void validateLuggage(Luggage luggage);

    enum ValidationMode {ECONOMY}
}