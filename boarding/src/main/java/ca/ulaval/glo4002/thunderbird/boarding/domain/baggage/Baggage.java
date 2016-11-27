package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Inheritance
public class Baggage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID baggageHash;
    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Length linearDimension;
    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Mass weight;
    private String type;
    private float price;

    public Baggage(Length linearDimension, Mass weight, String type) {
        this.baggageHash = UUID.randomUUID();
        this.linearDimension = linearDimension;
        this.weight = weight;
        this.type = type;
    }

    public Baggage(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        this.baggageHash = baggageHash;
        this.linearDimension = linearDimension;
        this.weight = weight;
        this.type = type;
    }

    protected Baggage() {
        // for hibernate
    }

    public UUID getId() {
        return baggageHash;
    }

    public Mass getWeight() {
        return weight;
    }

    public Length getDimension() {
        return linearDimension;
    }

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void validate(int baggageDimensionLimitInMm, int baggageWeightLimitInGrams) {
        if (weight.toGrams() > baggageWeightLimitInGrams) {
            throw new BaggageWeightInvalidException();
        }

        if (linearDimension.toMillimeters() > baggageDimensionLimitInMm) {
            throw new BaggageDimensionInvalidException();
        }
    }
}