package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageFormatUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Overweight;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StandardBaggageCollection extends BaggageCollection {
    private static final String TYPE = "standard";
    private static final int BAGGAGES_LIMIT = 1;
    private static final float TOTAL_COST = 0;

    public StandardBaggageCollection() {
        collection = new ArrayList<>();
    }

    @Override
    public void addBaggage(Baggage baggage) {
        validateCollection(baggage);
        baggage.setBaggageCollection(this);
        collection.add(baggage);
    }

    @Override
    protected void validateCollection(Baggage baggage) {
        if (collection.size() >= BAGGAGES_LIMIT) {
            throw new BaggageAmountUnauthorizedException();
        }
        if (baggage.hasSpeciality(new Oversize())) {
            throw new BaggageFormatUnauthorizedException();
        }
        if (baggage.hasSpeciality(new Overweight())) {
            throw new BaggageFormatUnauthorizedException();
        }
    }

    @Override
    public double calculateTotalCost() {
        return TOTAL_COST;
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
