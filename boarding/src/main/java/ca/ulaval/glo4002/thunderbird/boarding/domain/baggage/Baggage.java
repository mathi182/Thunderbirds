package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance
@DiscriminatorColumn(name = "BAGGAGE_TYPE")
public abstract class Baggage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    protected UUID baggageHash;
    protected LinearDimensionUnits linearDimensionUnit;
    protected Integer linearDimension;
    protected WeightUnits weightUnit;
    protected Integer weight;

    @ManyToOne
    @JoinColumn(name = "passengerHash")
    private Passenger passenger;

    public Baggage(LinearDimensionUnits linearDimensionUnit, Integer linearDimension, WeightUnits weightUnit, Integer weight) {
        this.baggageHash = UUID.randomUUID();
        this.linearDimension = linearDimension;
        this.linearDimensionUnit = linearDimensionUnit;
        this.weightUnit = weightUnit;
        this.weight = weight;
    }

    protected Baggage() {
        //for hibernate
    }

    public abstract void validate();

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
