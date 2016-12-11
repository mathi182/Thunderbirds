package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class BaggagesCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable=false,unique=true)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baggagesCollection", fetch = FetchType.EAGER)
    protected List<Baggage> collection;

    @ManyToOne(fetch=FetchType.EAGER)
    protected Passenger passenger;

    @ManyToOne(fetch=FetchType.EAGER)
    protected PassengerBaggagesCollection passengerBaggagesCollection;

    public abstract void addBaggage(Baggage baggage);
    protected abstract void validate(Baggage baggage);
    public abstract float calculateTotalCost();
    public abstract String getCollectionType();
    public abstract List<Baggage> getBaggages();

    public void setPassengerBaggagesCollection(PassengerBaggagesCollection passengerBaggagesCollection) {
        this.passengerBaggagesCollection = passengerBaggagesCollection;
        this.passenger = passengerBaggagesCollection.passenger;
    }
}
