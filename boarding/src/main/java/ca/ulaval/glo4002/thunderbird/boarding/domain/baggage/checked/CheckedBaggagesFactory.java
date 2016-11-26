package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.UUID;

public class CheckedBaggagesFactory {
    public static final int BUSINESS_FREE_CHECKED_BAGGAGE = 2;
    public static final int BUSINESS_WEIGHT_LIMIT_IN_GRAMS = 30000;
    public static final int BUSINESS_DIMENSION_LIMIT_IN_MM = 1580;

    public static final int ECONOMIC_FREE_CHECKED_BAGGAGE = 1;
    public static final int ECONOMIC_WEIGHT_LIMIT_IN_GRAMS = 23000;
    public static final int ECONOMIC_DIMENSION_LIMIT_IN_MM = 1580;

    public static CheckedBaggages getCheckedBaggages(UUID pasengerHash, Seat.SeatClass seatClass) {
        switch (seatClass) {
            case ECONOMY:
                return new CheckedBaggages(pasengerHash, ECONOMIC_FREE_CHECKED_BAGGAGE,
                        ECONOMIC_WEIGHT_LIMIT_IN_GRAMS, ECONOMIC_DIMENSION_LIMIT_IN_MM);
            case BUSINESS:
                return new CheckedBaggages(pasengerHash, BUSINESS_FREE_CHECKED_BAGGAGE,
                        BUSINESS_WEIGHT_LIMIT_IN_GRAMS, BUSINESS_DIMENSION_LIMIT_IN_MM);
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}