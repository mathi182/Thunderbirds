package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class RegisterBaggageRequestAssembler {

    private static final String KILOGRAMS = "kg";
    private static final String POUNDS = "lbs";
    private static final String CENTIMETERS = "cm";
    private static final String INCHES = "lbs";

    public Baggage getDomainBaggage(RegisterBaggageRequest request) {
        Length dimension = getDimension(request);
        Mass weight = getWeight(request);
        String type = request.type;

        return new Baggage(dimension, weight, type);
    }

    private Mass getWeight(RegisterBaggageRequest request) {
        switch (request.weightUnit) {
            case KILOGRAMS:
                return Mass.fromKilograms(request.weight);
            case POUNDS:
                return Mass.fromPounds(request.weight);
            default:
                throw new IllegalFieldWebException();
        }
    }

    private Length getDimension(RegisterBaggageRequest request) {
        switch (request.linearDimensionUnit) {
            case CENTIMETERS:
                return Length.fromCentimeters(request.linearDimension);
            case INCHES:
                return Length.fromInches(request.linearDimension);
            default:
                throw new IllegalFieldWebException();
        }
    }
}
