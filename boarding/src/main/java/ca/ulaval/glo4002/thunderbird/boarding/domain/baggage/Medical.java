package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.util.UUID;

public class Medical extends Baggage {
    private static final int BAGGAGE_COST = 50;
    private static final float OVERWEIGHT_BAGGAGE_FEES = 1.1f;
    private static final float SPORT_BAGGAGE_FEES = 1.25f;

    public Medical(UUID baggageHash, Length linearDimension, Mass weight) {
        super(baggageHash, linearDimension, weight);
    }

    public Medical(Length linearDimension, Mass weight) {
        super(linearDimension, weight);
    }

    public Medical() {
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
}
