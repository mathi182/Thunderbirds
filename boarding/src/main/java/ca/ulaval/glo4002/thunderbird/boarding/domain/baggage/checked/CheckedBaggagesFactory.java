package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.UUID;

public class CheckedBaggagesFactory {
    public static CheckedBaggages getCheckedBaggages(UUID pasengerHash, Seat.SeatClass seatClass) {
        switch (seatClass) {
            case ECONOMY:
                return new EconomicCheckedBagage(pasengerHash);
            case BUSINESS:
                return new BusinessCheckedBaggage(pasengerHash);
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}