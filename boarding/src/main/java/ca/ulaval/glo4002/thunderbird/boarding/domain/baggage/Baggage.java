package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Baggage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    protected UUID baggageHash;
    protected float price;

    @Embedded
    protected Speciality speciality;

    @Embedded
    protected Length linearDimension;

    @Embedded
    protected Mass weight;

    public Baggage(Length linearDimension, Mass weight, Speciality speciality) {
        this.baggageHash = UUID.randomUUID();
        this.linearDimension = linearDimension;
        this.weight = weight;
        this.speciality = speciality;
    }

    public Baggage(UUID baggageHash, Length linearDimension, Mass weight, Speciality speciality) {
        this.baggageHash = baggageHash;
        this.linearDimension = linearDimension;
        this.weight = weight;
        this.speciality = speciality;
    }

    protected Baggage() {
        // for hibernate
    }

    public abstract float getBasePrice(Length maximumLinearDimension, Mass maximumWeight);

    public abstract boolean isChecked();

    public UUID getId() {
        return baggageHash;
    }

    public Mass getWeight() {
        return weight;
    }

    public Length getDimension() {
        return linearDimension;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void validate(Length maximumLinearDimension, Mass maximumWeight) {
        if (weight.isSuperiorTo(maximumWeight)) {
            throw new BaggageWeightInvalidException();
        }
        if (linearDimension.isSuperiorTo(maximumLinearDimension)) {
            throw new BaggageDimensionInvalidException();
        }
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}