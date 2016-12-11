package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

public class CheckedBaggageCollectionFactory {
    public static CheckedBaggageCollection createCheckedBaggages(Passenger passenger) {
        switch (passenger.getSeatClass()) {
            case ECONOMY:
                return new EconomicBaggageCollection(passenger);
            case BUSINESS:
                return new BusinessBaggageCollection(passenger);
            default:
                throw new NoSuchStrategyException("unknown seatClass" + passenger.getSeatClass());
        }
    }
}