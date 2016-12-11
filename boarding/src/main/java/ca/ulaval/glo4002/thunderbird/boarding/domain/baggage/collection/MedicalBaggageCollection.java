package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MedicalBaggageCollection extends BaggagesCollection {
    private static final int COST = 0;
    private static final String TYPE = "medical";

    public MedicalBaggageCollection() {
        collection = new ArrayList<>();
    }

    @Override
    public void addBaggage(Baggage baggage) {
        baggage.setBaggagesCollection(this);
        collection.add(baggage);
    }

    @Override
    protected void validate(Baggage baggage) {
        //TODO Implement me.
    }

    @Override
    public float calculateTotalCost() {
        return COST;
    }

    @Override
    public String getCollectionType() {
        return TYPE;
    }

    @Override
    public List<Baggage> getBaggages() {
        return collection;
    }
}
