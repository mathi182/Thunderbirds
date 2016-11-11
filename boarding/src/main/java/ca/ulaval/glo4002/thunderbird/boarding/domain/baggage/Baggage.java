package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.omg.CORBA.Object;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance
@DiscriminatorColumn(name="BAGGAGE_TYPE")
public class Baggage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID baggageHash;

    @Column
    private int linearDimensionInMm;

    @Column
    private int weightInGrams;

    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name = "passenger")
    @JsonBackReference
    private Passenger passenger;

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

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
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

    @Override
    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Baggage.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        Baggage baggage = (Baggage) obj;
        boolean baggageHashAreEquals = baggageHash.equals(baggage.baggageHash);
        boolean weightsAreEquals = getWeightInGrams() == baggage.weightInGrams;
        boolean dimensionAreEquals = getDimensionInMm() == baggage.linearDimensionInMm;
        boolean typeAreEquals = type.equals(baggage.type);
        return baggageHashAreEquals && weightsAreEquals &&
                dimensionAreEquals && typeAreEquals;
    }
}
