package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageFormatUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Overweight;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PersonalBaggageCollection extends BaggageCollection {
    private static final String TYPE = "personal";
    private static final int BAGGAGES_LIMIT = 1;

    public PersonalBaggageCollection() {
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
            throw new BaggageDimensionInvalidException();
        }
        if (baggage.hasSpeciality(new Overweight())) {
            throw new BaggageWeightInvalidException();
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
