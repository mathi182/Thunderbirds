package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageDTO;

public class CheckedBaggageFactory {

    public Baggage createCheckedBaggage(Passenger passenger, RegisterBaggageDTO dto) {
        switch (passenger.getSeatClass()) {
            case ECONOMY:
                return new Economic();
            case BUSINESS:
                return new Business();
            default:
                throw new NoSuchStrategyException("unknown seatClass" + passenger.getSeatClass());
        }
    }
}
