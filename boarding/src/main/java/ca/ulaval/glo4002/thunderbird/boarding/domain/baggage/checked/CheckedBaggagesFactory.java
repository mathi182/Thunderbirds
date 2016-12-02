package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

public class CheckedBaggagesFactory {
    public static CheckedBaggages getCheckedBaggages(Passenger passenger) {
        switch (passenger.getSeatClass()) {
            case ECONOMY:
                return new EconomicCheckedBaggages(passenger);
            case BUSINESS:
                return new BusinessCheckedBaggages(passenger);
            default:
                throw new NoSuchStrategyException("unknown seatClass" + passenger.getSeatClass());
        }
    }
}