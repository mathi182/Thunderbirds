package ca.ulaval.glo4002.thunderbird.boarding.domain.luggage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import java.util.UUID;

@Entity
@Inheritance
public class Luggage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID luggageHash;
    private int linearDimensionInMm;
    private int weightInGrams;
    private String type;
    private float price;

    public Luggage(int linearDimensionInMm, int weightInG, String type) {
        this.luggageHash = UUID.randomUUID();
        this.linearDimensionInMm = linearDimensionInMm;
        this.weightInGrams = weightInG;
        this.type = type;
    }

    public Luggage(UUID luggageHash, int linearDimensionInMm, int weightInG, String type) {
        this.luggageHash = luggageHash;
        this.linearDimensionInMm = linearDimensionInMm;
        this.weightInGrams = weightInG;
        this.type = type;
    }

    public Luggage(UUID luggageHash, int linearDimensionInMm, int weightInG, String type, float price) {
        this(luggageHash, linearDimensionInMm, weightInG, type);
        this.price = price;
    }

    protected Luggage() {
        // for hibernate
    }

    public int getWeightInGrams() {
        return weightInGrams;
    }

    public int getDimensionInMm() {
        return linearDimensionInMm;
    }

    public UUID getLuggageHash() {
        return luggageHash;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Luggage.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        Luggage luggage = (Luggage) obj;
        boolean luggageHashAreEquals = luggageHash.equals(luggage.getLuggageHash());
        boolean weightsAreEquals = weightInGrams == luggage.getWeightInGrams();
        boolean dimensionAreEquals = linearDimensionInMm == luggage.getDimensionInMm();
        boolean typeAreEquals = type.equals(luggage.getType());

        return luggageHashAreEquals && weightsAreEquals &&
                dimensionAreEquals && typeAreEquals;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

