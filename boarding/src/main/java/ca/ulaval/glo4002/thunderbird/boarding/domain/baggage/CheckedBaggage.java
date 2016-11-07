package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;

public abstract class CheckedBaggage extends Baggage {
    public CheckedBaggage(LinearDimensionUnits linearDimensionUnit, Integer linearDimension, WeightUnits weightUnit, Integer weight) {
        super(linearDimensionUnit,linearDimension, weightUnit, weight);
    }

    @Override
    public void validate() {
        validateDimension();
        validateWeight();
    }

    private void validateDimension () {
        Integer dimensionlimit = getDimensionLimit();
        if (linearDimension > dimensionlimit) {
            throw new BaggageDimensionInvalidException();
        }
    }

    protected abstract int getDimensionLimit();

    private void validateWeight () {
        Integer weightLimit = getWeightLimit();
        if (weight > weightLimit) {
            throw new BaggageWeightInvalidException();
        }
    }

    protected abstract int getWeightLimit();
}