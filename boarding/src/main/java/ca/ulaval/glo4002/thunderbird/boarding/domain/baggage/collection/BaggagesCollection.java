package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class BaggagesCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable=false,unique=true)
    private int id;

    @OneToMany(mappedBy = "baggagesCollection")
    protected Set<Baggage> collection;

    @Transient
    protected Passenger passenger;

    @ManyToOne(fetch=FetchType.LAZY)
    private PassengerBaggagesCollection passengerBaggagesCollection;

    public abstract void addBaggage(Baggage baggage);
    protected abstract void validate(Baggage baggage);
    public abstract float calculateTotalCost();
    public abstract String getCollectionType();
    public abstract Set<Baggage> getBaggages();
}
