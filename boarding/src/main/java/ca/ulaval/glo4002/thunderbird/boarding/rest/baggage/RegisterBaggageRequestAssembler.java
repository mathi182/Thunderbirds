package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.CheckedBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.DimensionConverter;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.DimensionConverterFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.WeightConverter;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.WeightConverterFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.EconomyCheckedBaggageValidationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.MissingFieldException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions.IllegalFieldWebException;
import ca.ulaval.glo4002.thunderbird.boarding.util.Strings;

public class RegisterBaggageRequestAssembler {
    public Baggage getDomainBaggage(RegisterBaggageRequest request) {
        validateRequest(request);

        int dimension = convertRequestDimension(request);
        int weight = convertRequestWeight(request);

        return new Baggage(dimension, weight);
    }

    private int convertRequestDimension(RegisterBaggageRequest request) {
        DimensionConverterFactory dimensionConverterFactory = new DimensionConverterFactory();
        DimensionConverter dimensionConverter = dimensionConverterFactory.getConverter(request.linearDimensionUnit);
        int convertedDimension = dimensionConverter.convertToMillimeters(request.linearDimension);
        return convertedDimension;
    }

    private int convertRequestWeight(RegisterBaggageRequest request) {
        WeightConverterFactory weightConverterFactory = new WeightConverterFactory();
        WeightConverter weightConverter = weightConverterFactory.getConverter(request.weightUnit);
        int convertedWeight = weightConverter.convertToGrams(request.weight);
        return convertedWeight;
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
            throw new MissingFieldException("weightInG");
        }
    }
}
