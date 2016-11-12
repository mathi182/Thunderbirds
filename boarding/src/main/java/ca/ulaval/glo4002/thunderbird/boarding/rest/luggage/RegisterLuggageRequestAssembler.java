package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.unitConverters.DimensionConverter;
import ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.unitConverters.DimensionConverterFactory;
import ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.unitConverters.WeightConverter;
import ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.unitConverters.WeightConverterFactory;

public class RegisterLuggageRequestAssembler {
    public Luggage getDomainLuggage(RegisterLuggageRequest request) {
        int dimension = convertRequestDimension(request);
        int weight = convertRequestWeight(request);
        String type = request.type;

        return new Luggage(dimension, weight, type);
    }

    private int convertRequestDimension(RegisterLuggageRequest request) {
        DimensionConverterFactory dimensionConverterFactory = new DimensionConverterFactory();
        DimensionConverter dimensionConverter = dimensionConverterFactory.getConverter(request.linearDimensionUnit);
        return dimensionConverter.convertToMillimeters(request.linearDimension);
    }

    private int convertRequestWeight(RegisterLuggageRequest request) {
        WeightConverterFactory weightConverterFactory = new WeightConverterFactory();
        WeightConverter weightConverter = weightConverterFactory.getConverter(request.weightUnit);
        return weightConverter.convertToGrams(request.weight);
    }
}
