package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class PersonalBaggage extends Baggage {
    private static final String TYPE = "personal";

    public PersonalBaggage(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        super(baggageHash, linearDimension, weight, type);
    }

    public PersonalBaggage(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    public PersonalBaggage() {
        // For hibernate
    }

    @Override
    public float getBasePrice() {
        return 0;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
