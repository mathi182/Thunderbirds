package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy.BaggagePriceCalculatorDTO;

public class CheckedBaggagePriceStrategy  implements BaggagePriceStrategy {

    @Override
    public float calculatePrice(BaggagePriceCalculatorDTO passengerBaggagePriceInformation) {
        if (passengerBaggagePriceInformation.numberOfBaggagesOfSameType <
                passengerBaggagePriceInformation.freeCheckedBaggageCount) {
            return 0;
        }

        return passengerBaggagePriceInformation.costingCheckedBaggageCost;
    }
}
