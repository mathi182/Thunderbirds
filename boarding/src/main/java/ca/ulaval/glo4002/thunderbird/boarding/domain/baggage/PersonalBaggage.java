package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class PersonalBaggage extends Baggage {
    private static final String TYPE = "personal";
    private static final float PRICE = 0;
    public static final Length MAXIMUM_LENGTH = Length.fromCentimeters(92);
    public static final Mass MAXIMUM_WEIGHT = Mass.fromKilograms(10);



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
