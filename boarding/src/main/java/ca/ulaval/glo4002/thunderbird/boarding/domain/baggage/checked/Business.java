package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.util.UUID;

public class Business extends CheckedBaggage{

    public Business(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        super(baggageHash, linearDimension, weight, type);
    }

    public Business(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }
}
