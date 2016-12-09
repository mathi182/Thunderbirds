package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CheckedBaggage extends Baggage {
    protected static final int BAGGAGE_COST = 50;
    protected static final float OVERWEIGHT_BAGGAGE_FEES = 1.1f;
    protected static final float SPORT_BAGGAGE_FEES = 1.25f;

    public CheckedBaggage(UUID baggageHash, Length linearDimension, Mass weight, Speciality speciality) {
        super(baggageHash, linearDimension, weight, speciality);
    }

    public CheckedBaggage(Length linearDimension, Mass weight, Speciality speciality) {
        super(linearDimension, weight, speciality);
    }

    protected CheckedBaggage() {
        // for hibernate
    }

    @Override
    public float getBasePrice(Length maximumLinearDimension, Mass maximumWeight) {
        float cost = speciality.getModificator() * BAGGAGE_COST;

        if (weight.isSuperiorTo(maximumWeight)) {
            cost *= OVERWEIGHT_BAGGAGE_FEES;
        }
        if (!isSportType() && linearDimension.isSuperiorTo(maximumLinearDimension)) {
            cost *= OVERWEIGHT_BAGGAGE_FEES;
        }
        return cost;
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    protected boolean isSportType() {
        return Objects.equals(getSpeciality(), "sport");
    }
}