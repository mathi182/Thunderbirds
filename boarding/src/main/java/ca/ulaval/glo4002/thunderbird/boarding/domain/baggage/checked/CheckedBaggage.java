package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CheckedBaggage extends Baggage {
    private static final int BAGGAGE_COST = 50;
    private static final float EXCESS_BAGGAGE_FEES = 1.1f;
    private static final float SPORT_BAGGAGE_FEES = 1.25f;

    public CheckedBaggage(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        super(baggageHash, linearDimension, weight, type);
    }

    public CheckedBaggage(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    protected CheckedBaggage() {
        // for hibernate
    }

    @Override
    public float getBasePrice(Length maximumLinearDimension, Mass maximumWeight) {
        float cost = isSport() ? BAGGAGE_COST * SPORT_BAGGAGE_FEES : BAGGAGE_COST;

        if (weight.isSuperiorTo(maximumWeight)) {
            cost *= EXCESS_BAGGAGE_FEES;
        }
        if (!isSport() && linearDimension.isSuperiorTo(maximumLinearDimension)) {
            cost *= EXCESS_BAGGAGE_FEES;
        }
        return cost;
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    private boolean isSport() {
        return Objects.equals(getType(), "sport");
    }
}