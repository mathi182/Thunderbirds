package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy;

public class BaggagePriceCalculatorDTO {
    public int numberOfBaggagesOfSameType;
    public int freeCheckedBaggageCount;
    public int costingCheckedBaggageCost;
    public int carryOnBaggageCost;

    public BaggagePriceCalculatorDTO(int numberOfBaggagesOfSameType,
                                     int freeCheckedBaggageCount,
                                     int costingCheckedBaggageCost,
                                     int carryOnBaggageCost) {
        this.numberOfBaggagesOfSameType = numberOfBaggagesOfSameType;
        this.freeCheckedBaggageCount = freeCheckedBaggageCount;
        this.costingCheckedBaggageCost = costingCheckedBaggageCost;
        this.carryOnBaggageCost = carryOnBaggageCost;
    }

    public BaggagePriceCalculatorDTO() {

    }
}
