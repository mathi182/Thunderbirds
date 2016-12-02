package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;

@Entity
public class BusinessCheckedBaggages extends CheckedBaggages {
    private static final int FREE_BAGGAGE_COUNT = 2;
    private static final Mass WEIGHT_LIMIT = Mass.fromKilograms(30);
    private static final Length DIMENSION_LIMIT = Length.fromCentimeters(158);

    public BusinessCheckedBaggages(Passenger passenger) {
        super(passenger);
    }

    protected BusinessCheckedBaggages() {
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
}