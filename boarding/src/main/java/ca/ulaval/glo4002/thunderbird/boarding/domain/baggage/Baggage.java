package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import java.util.UUID;

@Entity
@Inheritance
public class Baggage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID baggageHash;
    private int linearDimensionInMm;
    private int weightInGrams;
    private String type;
    private float price;

    public Baggage(int linearDimensionInMm, int weightInG, String type) {
        this.baggageHash = UUID.randomUUID();
        this.linearDimensionInMm = linearDimensionInMm;
        this.weightInGrams = weightInG;
        this.type = type;
    }

    public Baggage(UUID baggageHash, int linearDimensionInMm, int weightInG, String type) {
        this.baggageHash = baggageHash;
        this.linearDimensionInMm = linearDimensionInMm;
        this.weightInGrams = weightInG;
        this.type = type;
    }

    public Baggage(UUID baggageHash, int linearDimensionInMm, int weightInG, String type, float price) {
        this(baggageHash, linearDimensionInMm, weightInG, type);
        this.price = price;
    }

    protected Baggage() {
        // for hibernate
    }

    public int getWeightInGrams() {
        return weightInGrams;
    }

    public int getDimensionInMm() {
        return linearDimensionInMm;
    }

    public UUID getBaggageHash() {
        return baggageHash;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Baggage.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        Baggage baggage = (Baggage) obj;
        boolean baggageHashAreEquals = baggageHash.equals(baggage.getBaggageHash());
        boolean weightsAreEquals = weightInGrams == baggage.getWeightInGrams();
        boolean dimensionAreEquals = linearDimensionInMm == baggage.getDimensionInMm();
        boolean typeAreEquals = type.equals(baggage.getType());

        return baggageHashAreEquals && weightsAreEquals &&
                dimensionAreEquals && typeAreEquals;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void validate(int baggageDimensionLimitInMm, int baggageWeightLimitInGrams) {
        if (this.weightInGrams > baggageWeightLimitInGrams) {
            throw new BaggageWeightInvalidException();
        }

        if (this.linearDimensionInMm > baggageDimensionLimitInMm) {
            throw new BaggageDimensionInvalidException();
        }
    }
}

