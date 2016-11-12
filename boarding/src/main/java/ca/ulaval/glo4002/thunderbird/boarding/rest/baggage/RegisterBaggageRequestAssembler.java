package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters.DimensionConverter;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters.DimensionConverterFactory;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters.WeightConverter;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters.WeightConverterFactory;

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
