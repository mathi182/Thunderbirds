package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;

public class CheckedBaggageFactory {

    public Baggage createCheckedBaggage(Passenger passenger, NormalizedBaggageDTO dto) {

        switch (passenger.getSeatClass()) {
            case ECONOMY:
                return new Economic(dto.length, dto.mass);
            case BUSINESS:
                return new Business(dto.length, dto.mass);
            default:
                throw new NoSuchStrategyException("unknown seatClass" + passenger.getSeatClass());
        }
    }
}
