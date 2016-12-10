package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import java.util.List;

public abstract class BaggagesCollection {

    protected Passenger passenger;

    public abstract void addBaggage(Baggage baggage);
    protected abstract void validate(Baggage baggage);
    public abstract float calculateTotalCost();
    public abstract String getCollectionType();
    public abstract List<Baggage> getBaggages();
}
