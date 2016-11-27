package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class RegisterBaggageRequestAssembler {

    public Baggage getDomainBaggage(RegisterBaggageRequest request) {
        Length dimension = getDimension(request);
        Mass weight = getWeight(request);
        String type = request.type;

        return new Baggage(dimension, weight, type);
    }

    private Mass getWeight(RegisterBaggageRequest request) {
        switch (request.weightUnit) {
            case "kg":
                return Mass.fromKilograms(request.weight);
            case "lbs":
                return Mass.fromPounds(request.weight);
            default:
                throw new IllegalFieldWebException();
        }
    }

    private Length getDimension(RegisterBaggageRequest request) {
        switch (request.linearDimensionUnit) {
            case "cm":
                return Length.fromCentimeters(request.linearDimension);
            case "po":
                return Length.fromInches(request.linearDimension);
            default:
                throw new IllegalFieldWebException();
        }
    }
}
