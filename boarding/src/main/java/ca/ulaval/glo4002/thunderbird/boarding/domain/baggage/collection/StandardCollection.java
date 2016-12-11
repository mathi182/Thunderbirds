package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class StandardCollection extends BaggagesCollection {
    private static final String TYPE = "standard";

    public StandardCollection() {
        collection = new HashSet<>();
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
    public Set<Baggage> getBaggages() {
        return collection;
    }
}
