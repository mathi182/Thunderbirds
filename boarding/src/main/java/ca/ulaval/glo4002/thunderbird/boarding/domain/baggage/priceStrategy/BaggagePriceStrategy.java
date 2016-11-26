package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy.BaggagePriceCalculatorDTO;

public interface BaggagePriceStrategy {
    float calculatePrice(BaggagePriceCalculatorDTO baggagePriceCalculatorDTO);
}
