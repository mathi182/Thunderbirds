package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class BaggageCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable=false,unique=true)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baggageCollection", fetch = FetchType.EAGER)
    protected List<Baggage> collection;

    @ManyToOne(fetch=FetchType.EAGER)
    protected Passenger passenger;

    @ManyToOne(fetch=FetchType.EAGER)
    protected PassengerBaggageCollections passengerBaggageCollections;

    public abstract void addBaggage(Baggage baggage);
    protected abstract void validate(Baggage baggage);
    public abstract float calculateTotalCost();
    public abstract String getCollectionType();
    public abstract List<Baggage> getBaggages();

    public void setPassengerBaggageCollections(PassengerBaggageCollections passengerBaggageCollections) {
        this.passengerBaggageCollections = passengerBaggageCollections;
        this.passenger = passengerBaggageCollections.passenger;
    }
}
