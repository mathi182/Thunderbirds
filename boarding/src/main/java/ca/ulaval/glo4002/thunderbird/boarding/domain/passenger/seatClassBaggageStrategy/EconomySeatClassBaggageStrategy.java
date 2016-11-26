package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy;

public class EconomySeatClassBaggageStrategy extends SeatClassBaggageTemplate {
    public static final int WEIGHT_LIMIT_IN_GRAMS = 23000;
    public static final int DIMENSION_LIMIT_IN_MM = 1580;
    public static final int CHECKED_BAGGAGE_COUNT_LIMIT = 3;
    public static final int FREE_CHECKED_BAGGAGE_COUNT = 1;
    public static final int COSTING_CHECKED_BAGGAGE_COST = 50;
    public static final int CARRY_ON_BAGGAGE_COST = 30;


    @Override
    BaggageValidationDTO getBaggageValidationDTO(int numberOfBaggagesOfSameType) {
        return new BaggageValidationDTO(numberOfBaggagesOfSameType,
                WEIGHT_LIMIT_IN_GRAMS,
                DIMENSION_LIMIT_IN_MM,
                CHECKED_BAGGAGE_COUNT_LIMIT);
    }

    @Override
    BaggagePriceCalculatorDTO getBaggagePriceCalculatorDTO(int numberOfBaggagesOfSameType) {
        return new BaggagePriceCalculatorDTO(numberOfBaggagesOfSameType,
                FREE_CHECKED_BAGGAGE_COUNT,
                COSTING_CHECKED_BAGGAGE_COST,
                CARRY_ON_BAGGAGE_COST);
    }
}
