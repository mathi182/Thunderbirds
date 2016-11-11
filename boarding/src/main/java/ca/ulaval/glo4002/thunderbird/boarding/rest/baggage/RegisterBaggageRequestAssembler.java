package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.DimensionConverter;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.DimensionConverterFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.WeightConverter;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters.WeightConverterFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.MissingFieldException;
import ca.ulaval.glo4002.thunderbird.boarding.util.Strings;

public class RegisterBaggageRequestAssembler {
    public Baggage getDomainBaggage(RegisterBaggageRequest request) {
        int dimension = convertRequestDimension(request);
        int weight = convertRequestWeight(request);
        String type = request.type;

        return new Baggage(dimension, weight, type);
    }

    private int convertRequestDimension(RegisterBaggageRequest request) {
        DimensionConverterFactory dimensionConverterFactory = new DimensionConverterFactory();
        DimensionConverter dimensionConverter = dimensionConverterFactory.getConverter(request.linearDimensionUnit);
        return dimensionConverter.convertToMillimeters(request.linearDimension);
    }

    private int convertRequestWeight(RegisterBaggageRequest request) {
        WeightConverterFactory weightConverterFactory = new WeightConverterFactory();
        WeightConverter weightConverter = weightConverterFactory.getConverter(request.weightUnit);
        return weightConverter.convertToGrams(request.weight);
    }
}
