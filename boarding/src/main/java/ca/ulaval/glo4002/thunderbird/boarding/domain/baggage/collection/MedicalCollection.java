package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

import java.util.ArrayList;
import java.util.List;

public class MedicalCollection extends BaggagesCollection {

    private static final int COST = 0;
    private static final String TYPE = "medical";
    private List<Baggage> collection;

    public MedicalCollection() {
        collection = new ArrayList<>();
    }

    @Override
    public void addBaggage(Baggage baggage) {
        collection.add(baggage);
    }

    @Override
    protected void validate(Baggage baggage) {

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
