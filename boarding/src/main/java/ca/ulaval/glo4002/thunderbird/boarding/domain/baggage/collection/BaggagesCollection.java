package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
public abstract class BaggagesCollection {

    @Id
    @GeneratedValue
    private int id;

    protected Passenger passenger;

    public abstract void addBaggage(Baggage baggage);
    protected abstract void validate(Baggage baggage);
    public abstract float calculateTotalCost();
    public abstract String getCollectionType();
    public abstract List<Baggage> getBaggages();
}
