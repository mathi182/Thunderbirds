package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageFormatUnauthorizedException;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StandardBaggageCollection extends BaggageCollection {
    private static final String TYPE = "standard";
    private static final int BAGGAGES_LIMIT = 1;

    public StandardBaggageCollection() {
        collection = new ArrayList<>();
    }

    @Override
    public void addBaggage(Baggage baggage) {
        baggage.setBaggageCollection(this);
    }

    @Override
    protected void validate(Baggage baggage) {
        if (collection.size() >= BAGGAGES_LIMIT) {
            throw new BaggageAmountUnauthorizedException();
        }
        if ( baggage.hasSpecialities()) {
            throw new BaggageFormatUnauthorizedException();
        }
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
