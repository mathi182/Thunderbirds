package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy.BaggageValidationDTO;

public interface BaggageValidationStrategy {
    void validate(Baggage baggage, BaggageValidationDTO baggageValidationDTO);
}
