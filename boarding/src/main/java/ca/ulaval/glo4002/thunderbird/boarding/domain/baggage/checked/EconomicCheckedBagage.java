package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class EconomicCheckedBagage extends CheckedBaggages {
    public static final int FREE_CHECKED_BAGGAGE = 1;
    public static final int WEIGHT_LIMIT_IN_GRAMS = 23000;
    public static final int DIMENSION_LIMIT_IN_MM = 1580;

    public EconomicCheckedBagage(UUID passengerHash) {
        super(passengerHash);
    }

    protected EconomicCheckedBagage() {
        //for hibernate
    }

    @Override
    protected void validateBaggage(Baggage baggage) {
        baggage.validate(DIMENSION_LIMIT_IN_MM, WEIGHT_LIMIT_IN_GRAMS);
    }

    @Override
    protected void setBaggagePrice(Baggage baggage) {
        if (baggages.size() + 1 == FREE_CHECKED_BAGGAGE) {
            baggage.setPrice(0);
        } else {
            baggage.setPrice(CHECKED_BAGGAGE_COST);
        }
    }
}