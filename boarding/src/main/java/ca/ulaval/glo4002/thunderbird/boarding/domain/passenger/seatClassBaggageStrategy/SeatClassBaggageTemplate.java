package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.baggageStrategies.BaggageStrategies;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy.BaggagePriceStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategy;

public abstract class SeatClassBaggageTemplate {

    public void validateNewBaggage(Baggage baggage, int numberOfBaggagesOfSameType) {
        BaggageValidationDTO validationInformation = getBaggageValidationDTO(numberOfBaggagesOfSameType);
        BaggageStrategies baggageStrategies = BaggageStrategies.getFactory(baggage.getType());
        BaggageValidationStrategy validator = baggageStrategies.createValidationStrategy();
        validator.validate(baggage, validationInformation);
    }

    abstract BaggageValidationDTO getBaggageValidationDTO(int numberOfBaggagesOfSameType);

    public float calculateNewBaggagePrice(Baggage baggage, int numberOfBaggagesOfSameType) {
        BaggagePriceCalculatorDTO baggagePriceCalculatorDTO = getBaggagePriceCalculatorDTO(numberOfBaggagesOfSameType);
        BaggageStrategies baggageStrategies = BaggageStrategies.getFactory(baggage.getType());
        BaggagePriceStrategy calculator = baggageStrategies.createPriceStrategy();
        return calculator.calculatePrice(baggagePriceCalculatorDTO);
    }

    abstract BaggagePriceCalculatorDTO getBaggagePriceCalculatorDTO(int numberOfBaggagesOfSameType);
}
