package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class MedicalCollection extends BaggagesCollection {

    private static final int COST = 0;
    private static final String TYPE = "medical";

    public MedicalCollection() {
        collection = new HashSet<>();
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
    public Set<Baggage> getBaggages() {
        return collection;
    }
}
