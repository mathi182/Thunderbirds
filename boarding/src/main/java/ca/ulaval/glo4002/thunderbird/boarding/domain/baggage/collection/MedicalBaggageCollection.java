package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageFormatUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Overweight;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MedicalBaggageCollection extends BaggageCollection {
    private static final int COST = 0;
    private static final String TYPE = "medical";

    public MedicalBaggageCollection() {
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
        if (baggage.hasSpeciality(new Oversize())) {
            throw new BaggageFormatUnauthorizedException();
        }
        if (baggage.hasSpeciality(new Overweight())) {
            throw new BaggageFormatUnauthorizedException();
        }
    }

    @Override
    public double calculateTotalCost() {
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
