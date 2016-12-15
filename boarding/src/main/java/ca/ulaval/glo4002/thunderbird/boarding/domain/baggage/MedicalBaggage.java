package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class MedicalBaggage extends StandardBaggage {
    private static final String TYPE = "medical";
    private static final float PRICE = 0;

    public MedicalBaggage(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        super(baggageHash, linearDimension, weight, type);
    }

    public MedicalBaggage(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    public MedicalBaggage() {
        // For hibernate
    }

    @Override
    public float getBasePrice() {
        return PRICE;
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
