package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BusinessSeatClassBaggageStrategy extends SeatClassBaggageTemplate {
    @Override
    BaggageValidationDTO getBaggageValidationDTO(int numberOfBaggagesOfSameType) {
        throw new NotImplementedException();
    }

    @Override
    BaggagePriceCalculatorDTO getBaggagePriceCalculatorDTO(int numberOfBaggagesOfSameType) {
        throw new NotImplementedException();
    }
}
