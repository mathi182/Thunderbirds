package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;

@Entity
public class EconomicBaggageCollection extends CheckedBaggageCollection {
    private static final int FREE_BAGGAGE_COUNT = 1;
    private static final Mass WEIGHT_LIMIT = Mass.fromKilograms(23);
    private static final Length DIMENSION_LIMIT = Length.fromCentimeters(158);

    public EconomicBaggageCollection(Passenger passenger) {
        super(passenger);
    }

    protected EconomicBaggageCollection() {
        //for hibernate
    }

    @Override
    protected Mass getWeightLimit() {
        return WEIGHT_LIMIT;
    }

    @Override
    protected Length getDimensionLimit() {
        return DIMENSION_LIMIT;
    }

    @Override
    protected int getFreeBaggageCount() {
        return FREE_BAGGAGE_COUNT;
    }

    @Override
    public float calculateTotalCost() {
        float cost = 0;
        int baggageNumber = 1;
        for (Baggage currentBaggage : collection) {
            if (baggageNumber > FREE_BAGGAGE_COUNT) {
                cost += currentBaggage.getBasePrice();
            }
            baggageNumber++;
        }
        return cost;
    }

    @Override
    public String getCollectionType() {
        return TYPE;
    }
}