package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

import java.util.ArrayList;
import java.util.List;

public class StandardCollection extends BaggagesCollection {
    private static final String TYPE = "standard";
    private List<Baggage> collection;

    public StandardCollection() {
        collection = new ArrayList<>();
    }

    @Override
    public void addBaggage(Baggage baggage) {

    }

    @Override
    protected void validate(Baggage baggage) {

    }

    @Override
    public float calculateTotalCost() {
        return 0;
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
