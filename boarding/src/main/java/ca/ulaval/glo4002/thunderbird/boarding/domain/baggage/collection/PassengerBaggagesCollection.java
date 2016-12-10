package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.*;

@Entity
public class PassengerBaggagesCollection {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID checkedBaggageId;

    @OneToOne(fetch = FetchType.EAGER)
    protected Passenger passenger;

    @OneToMany(mappedBy = "passengerBaggagesCollection", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    protected Set<BaggagesCollection> collection;

    public PassengerBaggagesCollection(Passenger passenger) {
        this.checkedBaggageId = passenger.getHash();
        this.passenger = passenger;
        collection = new HashSet<>();
    }

    protected  PassengerBaggagesCollection() {
        //For Hibernate
    }

    public void addBaggage(Baggage baggage) {
        Optional<BaggagesCollection> baggagesCollection =
                collection.stream()
                .filter(x -> x.getCollectionType()
                        .equals(baggage.getType()))
                .findFirst();

        if (baggagesCollection.isPresent()) {
            baggagesCollection.get().addBaggage(baggage);
        } else {
            CollectionFactory collectionFactory = ServiceLocator.resolve(CollectionFactory.class);
            BaggagesCollection newCollection = collectionFactory.createCollection(passenger, baggage.getType());
            newCollection.addBaggage(baggage);
            collection.add(newCollection);
        }
    }

    public float calculateTotalPrice() {
        return collection.stream()
                .map(BaggagesCollection::calculateTotalCost)
                .reduce(0f, (a, b) -> a + b);
    }

    public Set<Baggage> getBaggages() {
        Set<Baggage> baggageList = new HashSet<>();

        for (BaggagesCollection baggagesCollection : collection) {
            baggageList.addAll(baggagesCollection.getBaggages());
        }

        return baggageList;
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
