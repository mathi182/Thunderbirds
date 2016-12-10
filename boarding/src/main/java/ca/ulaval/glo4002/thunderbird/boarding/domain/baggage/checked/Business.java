package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.util.UUID;

public class Business extends CheckedBaggage{

    public Business(UUID baggageHash, Length linearDimension, Mass weight) {
        super(baggageHash, linearDimension, weight);
    }

    public Business(Length linearDimension, Mass weight) {
        super(linearDimension, weight);
    }
}
