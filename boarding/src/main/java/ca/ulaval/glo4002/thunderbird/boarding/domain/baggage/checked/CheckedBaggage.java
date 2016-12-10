package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class CheckedBaggage extends Baggage {
    protected static final int BAGGAGE_COST = 50;
    protected static final String TYPE = "checked";

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
        float cost = BAGGAGE_COST;

        for (Speciality speciality : specialities) {
            cost *= speciality.getSpecialityFee();
        }

        return cost;
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}