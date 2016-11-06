package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

public class CheckedBaggageEconomy extends CheckedBaggage {
    public static final int WEIGHT_LIMIT_LBS = 50;
    public static final int WEIGHT_LIMIT_KG = 23;
    public static final int DIMENSION_LIMIT_PO = 62;
    public static final int DIMENSTION_LIMIT_CM = 158;

    public CheckedBaggageEconomy(LinearDimensionUnits linearDimensionUnit, Integer linearDimension, WeightUnits weightUnit, Integer weight) {
        super(linearDimensionUnit, linearDimension, weightUnit, weight);
    }

    @Override
    protected int getDimensionLimit() {
        if (linearDimensionUnit.equals(LinearDimensionUnits.CM)) {
            return DIMENSTION_LIMIT_CM;
        } else if (linearDimension.equals(LinearDimensionUnits.PO)) {
            return DIMENSION_LIMIT_PO;
        } else {
            return 0;
        }
    }

    @Override
    protected int getWeightLimit() {
        if (weightUnit.equals(WeightUnits.KG)) {
            return WEIGHT_LIMIT_KG;
        } else if (weightUnit.equals(WeightUnits.LBS)) {
            return WEIGHT_LIMIT_LBS;
        } else {
            return 0;
        }
    }
}
