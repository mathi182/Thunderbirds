package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.Seat.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

public class SeatClassBaggageStrategyFactory {
    public SeatClassBaggageTemplate getStrategy(Seat.SeatClass seatClass) {
        switch (seatClass) {
            case ECONOMY:
                return new EconomySeatClassBaggageStrategy();
            case BUSINESS:
                return new BusinessSeatClassBaggageStrategy();
            default:
                throw  new NoSuchStrategyException("unknown");
        }
    }
}
