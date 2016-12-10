package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.util.UUID;

public class Economic extends CheckedBaggage{

    public Economic(UUID baggageHash, Length linearDimension, Mass weight) {
        super(baggageHash, linearDimension, weight);
    }

    public Economic(Length linearDimension, Mass weight) {
        super(linearDimension, weight);
    }
}
