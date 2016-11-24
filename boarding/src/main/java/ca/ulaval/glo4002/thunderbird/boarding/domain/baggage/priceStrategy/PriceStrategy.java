package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

public interface PriceStrategy {
    void calculateBaggagePrice(Passenger passenger);
}
