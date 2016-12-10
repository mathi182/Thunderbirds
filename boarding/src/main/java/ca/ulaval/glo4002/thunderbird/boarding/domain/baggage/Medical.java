package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.util.UUID;

public class Medical extends Baggage {
    private static final String TYPE = "medical";

    public Medical(UUID baggageHash, Length linearDimension, Mass weight, String type) {
        super(baggageHash, linearDimension, weight, type);
    }

    public Medical(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    public Medical() {
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
