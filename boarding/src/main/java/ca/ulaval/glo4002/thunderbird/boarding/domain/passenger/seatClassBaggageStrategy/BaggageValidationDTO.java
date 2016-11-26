package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy;

public class BaggageValidationDTO {
    public int numberOfBaggagesOfSameType;
    public int weightLimitInGrams;
    public int dimensionLimitInMm;
    public int checkedBaggageCountLimit;

    public BaggageValidationDTO() {
    }

    public BaggageValidationDTO(int numberOfBaggagesOfSameType,
                                int weightLimitInGrams,
                                int dimensionLimitInMm,
                                int checkedBaggageCountLimit) {
        this.numberOfBaggagesOfSameType = numberOfBaggagesOfSameType;
        this.weightLimitInGrams = weightLimitInGrams;
        this.dimensionLimitInMm = dimensionLimitInMm;
        this.checkedBaggageCountLimit = checkedBaggageCountLimit;
    }
}
