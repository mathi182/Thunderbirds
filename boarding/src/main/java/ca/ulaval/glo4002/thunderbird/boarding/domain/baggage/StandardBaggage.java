package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class StandardBaggage extends Baggage {
    private static final String TYPE = "standard";

    public StandardBaggage(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        super(baggageHash, linearDimension, weight, type);
    }

    public StandardBaggage(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    public StandardBaggage() {
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
