package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Business extends CheckedBaggage{
    public static final Mass MAX_WEIGHT = Mass.fromKilograms(30);
    public static final Length MAX_LENGTH = Length.fromCentimeters(158);

    public Business(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        super(baggageHash, linearDimension, weight, type);
    }

    public Business(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }
}
