package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterBaggageRequestAssemblerTest {
    private static final String DIMENSION_UNIT_FROM_REQUEST = "cm";
    private static final int LINEAR_DIMENSION = 10;
    private static final String WEIGHT_UNIT_FROM_REQUEST = "kg";
    private static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    private static final int WEIGHT = 10;
    private static final String INVALID_UNIT = "invalid";
    private static final int WEIGHT_UNIT_CONVERTED = 10000;
    private static final int LINEAR_DIMENSION_CONVERTED = 100;
    private static final String VALID_BAGGAGE_TYPE = "checked";

    @Test
    public void givenValidRequest_whenGetDomainBaggage_shouldReturnBaggage() {
        RegisterBaggageDTO registerBaggage = new RegisterBaggageDTO(DIMENSION_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_FROM_REQUEST,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageAssembler registerBaggageAssembler = new RegisterBaggageAssembler();
        Baggage actualBaggage = registerBaggageAssembler.getDomainBaggage(registerBaggage);
        int actualLength = (int) actualBaggage.getDimension().toMillimeters();
        int actualMass = (int) actualBaggage.getWeight().toGrams();

        assertEquals(LINEAR_DIMENSION_CONVERTED, actualLength);
        assertEquals(WEIGHT_UNIT_CONVERTED, actualMass);
        assertEquals(VALID_BAGGAGE_TYPE, actualBaggage.getType());
    }

    @Test(expected = IllegalFieldWebException.class)

    public void givenInvalidWeightUnit_whenGetDomainBaggage_shouldThrowMissingFieldException() {
        RegisterBaggageDTO registerBaggage = new RegisterBaggageDTO(DIMENSION_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                INVALID_UNIT,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageAssembler registerBaggageAssembler = new RegisterBaggageAssembler();
        registerBaggageAssembler.getDomainBaggage(registerBaggage);
    }

    @Test(expected = IllegalFieldWebException.class)
    public void givenInvalidDimensionUnit_whenGetDomainBaggage_shouldThrowMissingFieldException() {
        RegisterBaggageDTO registerBaggage = new RegisterBaggageDTO(INVALID_UNIT,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_FROM_REQUEST,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageAssembler registerBaggageAssembler = new RegisterBaggageAssembler();
        registerBaggageAssembler.getDomainBaggage(registerBaggage);
    }
}