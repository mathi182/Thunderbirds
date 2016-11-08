package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance
@DiscriminatorColumn(name="BAGGAGE_TYPE")
public abstract class Baggage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    protected UUID baggageHash;

    @Column
    protected LinearDimensionUnits linearDimensionUnit;

    @Column
    protected Integer linearDimension;

    @Column
    protected WeightUnits weightUnit;

    @Column
    protected Integer weight;

    @ManyToOne
    @JoinColumn(name = "passenger")
    @JsonBackReference
    private Passenger passenger;

    public abstract void validate();

    public Baggage(LinearDimensionUnits linearDimensionUnit, Integer linearDimension, WeightUnits weightUnit, Integer weight) {
        this.baggageHash = UUID.randomUUID();
        this.linearDimension = linearDimension;
        this.linearDimensionUnit = linearDimensionUnit;
        this.weightUnit = weightUnit;
        this.weight = weight;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
