package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.util.UUID;

public class Standard extends Baggage {
    private static final String TYPE = "standard";

    public Standard(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        super(baggageHash, linearDimension, weight, type);
    }

    public Standard(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    public Standard() {
        // For hibernate
    }

    @Override
    public float getBasePrice(Length maximumLinearDimension, Mass maximumWeight) {
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
