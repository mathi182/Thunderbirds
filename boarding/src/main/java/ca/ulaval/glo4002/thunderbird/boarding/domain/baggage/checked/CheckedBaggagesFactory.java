package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.UUID;

public class CheckedBaggagesFactory {
    //TODO: Sortir ces constantes dans un fichier (Resource Bundle)
    private static final int CHECKED_BAGGAGE_COST = 50;
    private static final int CHECKED_BAGGAGE_COUNT_LIMIT = 3;

    private static final int BUSINESS_FREE_CHECKED_BAGGAGE = 2;
    private static final int BUSINESS_WEIGHT_LIMIT_IN_GRAMS = 30000;
    private static final int BUSINESS_DIMENSION_LIMIT_IN_MM = 1580;

    private static final int ECONOMIC_FREE_CHECKED_BAGGAGE = 1;
    private static final int ECONOMIC_WEIGHT_LIMIT_IN_GRAMS = 23000;
    private static final int ECONOMIC_DIMENSION_LIMIT_IN_MM = 1580;

    public static CheckedBaggages getCheckedBaggages(Seat.SeatClass seatClass) {
        switch (seatClass) {
            case ECONOMY:
                return new CheckedBaggages(ECONOMIC_FREE_CHECKED_BAGGAGE, CHECKED_BAGGAGE_COST,
                        CHECKED_BAGGAGE_COUNT_LIMIT, ECONOMIC_WEIGHT_LIMIT_IN_GRAMS, ECONOMIC_DIMENSION_LIMIT_IN_MM);
            case BUSINESS:
                return new CheckedBaggages(BUSINESS_FREE_CHECKED_BAGGAGE, CHECKED_BAGGAGE_COST,
                        CHECKED_BAGGAGE_COUNT_LIMIT, BUSINESS_WEIGHT_LIMIT_IN_GRAMS, BUSINESS_DIMENSION_LIMIT_IN_MM);
            default:
                throw new NoSuchStrategyException("unknown seatClass" + seatClass);
        }
    }
}