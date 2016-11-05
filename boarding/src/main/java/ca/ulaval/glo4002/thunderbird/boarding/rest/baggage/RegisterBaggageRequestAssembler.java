package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.CheckedBaggageEconomy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.MissingFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RegisterBaggageRequestAssembler {
    public Baggage getDomainBaggage(RegisterBaggageRequest request) {
        validateRequest(request);

        if (request.type.equals("checked")) {
            LinearDimensionUnits linearDimensionUnits = LinearDimensionUnits.valueOf(request.linearDimensionUnit);
            WeightUnits weightUnits = WeightUnits.valueOf(request.weightUnit);
            return new CheckedBaggageEconomy(linearDimensionUnits, request.linearDimension, weightUnits, request.weight);
        }
        else {
            throw new NotImplementedException();
        }
    }

    private void validateRequest(RegisterBaggageRequest request) {
        if (Strings.isNullOrEmpty(request.linearDimensionUnit)) {
            throw new MissingFieldException("linear_dimension_unit");
        }

        if (Strings.isNullOrEmpty(request.weightUnit)) {
            throw new MissingFieldException("weight_unit");
        }

        if (Strings.isNullOrEmpty(request.type)) {
            throw new MissingFieldException("type");
        }

        if (request.linearDimension == null) {
            throw new MissingFieldException("linear_dimension");
        }

        if (request.weight == null) {
            throw new MissingFieldException("weight");
        }
    }
}
