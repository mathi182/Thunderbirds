package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy.BaggageValidationDTO;

public class CheckedBaggageValidationStrategy implements BaggageValidationStrategy {
    @Override
    public void validate(Baggage baggage, BaggageValidationDTO baggageValidationDTO) {
        int countLimit = baggageValidationDTO.checkedBaggageCountLimit;
        int count = baggageValidationDTO.numberOfBaggagesOfSameType;
        if (count == countLimit) {
            throw new BaggageAmountUnauthorizedException();
        }

        baggage.validate(baggageValidationDTO.dimensionLimitInMm, baggageValidationDTO.weightLimitInGrams);
    }
}
