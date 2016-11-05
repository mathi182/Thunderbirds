package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

public abstract class Baggage {
    protected Integer linearDimension;
    protected LinearDimensionUnits linearDimensionUnit;
    protected WeightUnits weightUnit;
    protected Integer weight;

    public abstract void validate();

    public Baggage(LinearDimensionUnits linearDimensionUnit, Integer linearDimension, WeightUnits weightUnit, Integer weight) {
        this.linearDimension = linearDimension;
        this.linearDimensionUnit = linearDimensionUnit;
        this.weightUnit = weightUnit;
        this.weight = weight;
    }
}
