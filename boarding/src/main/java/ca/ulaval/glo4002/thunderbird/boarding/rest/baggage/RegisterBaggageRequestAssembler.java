package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.CheckedBaggageEconomy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.MissingFieldException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions.IllegalFieldWebException;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RegisterBaggageRequestAssembler {
    public Baggage getDomainBaggage(RegisterBaggageRequest request) {
        validateRequest(request);
        String weightUnitToUpper = request.weightUnit.toUpperCase();
        String dimensionUnitToUpper = request.linearDimensionUnit.toUpperCase();
        LinearDimensionUnits linearDimensionUnits = LinearDimensionUnits.valueOf(dimensionUnitToUpper);
        WeightUnits weightUnits = WeightUnits.valueOf(weightUnitToUpper);
        if (request.type.equals("checked")) {
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

        String dimensionUnitToUpper = request.linearDimensionUnit.toUpperCase();
        if (!dimensionUnitFromRequestIsValid(dimensionUnitToUpper)) {
            throw new IllegalFieldWebException();
        }

        String weightUnitToUpper = request.weightUnit.toUpperCase();
        if (!weightUnitFromRequestIsValid(weightUnitToUpper)) {
            throw new IllegalFieldWebException();
        }
    }

    private boolean dimensionUnitFromRequestIsValid(String dimensionUnitToUpper) {
        try {
            LinearDimensionUnits.valueOf(dimensionUnitToUpper);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private boolean weightUnitFromRequestIsValid(String weightUnitToUpper) {
        try {
            WeightUnits.valueOf(weightUnitToUpper);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
