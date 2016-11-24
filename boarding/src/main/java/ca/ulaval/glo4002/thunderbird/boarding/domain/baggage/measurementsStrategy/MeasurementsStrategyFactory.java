package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.measurementsStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

public class MeasurementsStrategyFactory {
    public BaggageValidationStrategy getStrategy(Seat.SeatClass seatClass) {
        switch (seatClass) {
            case ECONOMY:
                return new EconomyCheckedBaggageValidationStrategy();
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }

}