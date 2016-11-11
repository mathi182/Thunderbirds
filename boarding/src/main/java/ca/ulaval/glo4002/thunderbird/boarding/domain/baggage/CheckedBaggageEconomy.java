package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import javax.persistence.Entity;

@Entity
public class CheckedBaggageEconomy extends CheckedBaggage {
    public static final int WEIGHT_LIMIT_LBS = 50;
    public static final int WEIGHT_LIMIT_KG = 23;
    public static final int DIMENSION_LIMIT_PO = 62;
    public static final int DIMENSTION_LIMIT_CM = 158;

    public CheckedBaggageEconomy(LinearDimensionUnits linearDimensionUnit, Integer linearDimension, WeightUnits
            weightUnit, Integer weight) {
        super(linearDimensionUnit, linearDimension, weightUnit, weight);
    }

    protected CheckedBaggageEconomy() {
        //for hibernate
    }

    @Override
    protected int getDimensionLimit() {
        switch (linearDimensionUnit) {
            case CM:
                return DIMENSTION_LIMIT_CM;
            case PO:
                return DIMENSION_LIMIT_PO;
            default:
                return 0;
        }
    }

    @Override
    protected int getWeightLimit() {
        switch (weightUnit) {
            case KG:
                return WEIGHT_LIMIT_KG;
            case LBS:
                return WEIGHT_LIMIT_LBS;
            default:
                return 0;
        }
    }
}
