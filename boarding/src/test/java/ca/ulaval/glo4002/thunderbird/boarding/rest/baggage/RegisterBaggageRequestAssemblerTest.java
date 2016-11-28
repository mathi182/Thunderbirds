package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterBaggageRequestAssemblerTest {
    public static final String DIMENSION_UNIT_FROM_REQUEST = "cm";
    public static final int LINEAR_DIMENSION = 10;
    public static final String WEIGHT_UNIT_FROM_REQUEST = "kg";
    public static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    public static final int WEIGHT = 10;
    public static final String INVALID_UNIT = "invalid";
    public static final int WEIGHT_UNIT_CONVERTED = 10000;
    public static final int LINEAR_DIMENSION_CONVERTED = 100;
    public static final String VALID_BAGGAGE_TYPE = "checked";

    @Test
    public void givenValidRequest_whenGettingDomainBaggage_shouldReturnBaggage() {
        RegisterBaggageDTO registerBaggageRequest = new RegisterBaggageDTO(DIMENSION_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_FROM_REQUEST,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageAssembler registerBaggageRequestAssembler = new RegisterBaggageAssembler();
        Baggage actualBaggage = registerBaggageRequestAssembler.getDomainBaggage(registerBaggageRequest);
        int actualLength = (int) actualBaggage.getDimension().toMillimeters();
        int actualMass = (int) actualBaggage.getWeight().toGrams();

        assertEquals(LINEAR_DIMENSION_CONVERTED, actualLength);
        assertEquals(WEIGHT_UNIT_CONVERTED, actualMass);
        assertEquals(VALID_BAGGAGE_TYPE, actualBaggage.getType());
    }

    @Test(expected = IllegalFieldWebException.class)
    public void givenInvalidWeightUnit_whenGettingDomainBaggage_shouldThrowMissingFieldException() {
        RegisterBaggageDTO registerBaggageRequest = new RegisterBaggageDTO(DIMENSION_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                INVALID_UNIT,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageAssembler registerBaggageRequestAssembler = new RegisterBaggageAssembler();
        registerBaggageRequestAssembler.getDomainBaggage(registerBaggageRequest);
    }

    @Test(expected = IllegalFieldWebException.class)
    public void givenInvalidDimensionUnit_whenGettingDomainBaggage_shouldThrowMissingFieldException() {
        RegisterBaggageDTO registerBaggageRequest = new RegisterBaggageDTO(INVALID_UNIT,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_FROM_REQUEST,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageAssembler registerBaggageRequestAssembler = new RegisterBaggageAssembler();
        registerBaggageRequestAssembler.getDomainBaggage(registerBaggageRequest);
    }
}