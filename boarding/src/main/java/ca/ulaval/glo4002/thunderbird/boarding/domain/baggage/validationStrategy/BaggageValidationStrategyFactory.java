package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

public class BaggageValidationStrategyFactory {
    public BaggageValidationStrategy getStrategy(Seat.SeatClass seatClass) {
        switch (seatClass) {
            case ECONOMY:
                return new EconomyCheckedBaggageValidationStrategy();
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}