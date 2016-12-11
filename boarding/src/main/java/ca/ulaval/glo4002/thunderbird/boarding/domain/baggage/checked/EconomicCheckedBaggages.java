package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;

@Entity
public class EconomicCheckedBaggages extends CheckedBaggages {
    private static final int FREE_BAGGAGE_COUNT = 1;
    private static final Mass WEIGHT_LIMIT = Mass.fromKilograms(23);
    private static final Length DIMENSION_LIMIT = Length.fromCentimeters(158);

    public EconomicCheckedBaggages(Passenger passenger) {
        super(passenger);
    }

    protected EconomicCheckedBaggages() {
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
    protected void validate(Baggage baggage) {
        //TODO Implement me
    }

    @Override
    public float calculateTotalCost() {
        float cost = 0;
        int baggageProcessed = 0;
        for (Baggage currentBaggage : collection) {
            baggageProcessed++;
            if (baggageProcessed > FREE_BAGGAGE_COUNT) {
                cost += currentBaggage.getBasePrice();
            }
        }
        return cost;
    }

    @Override
    public String getCollectionType() {
        return TYPE;
    }
}