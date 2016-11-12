package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterLuggageRequestAssemblerTest {
    public static final String DIMENSION_UNIT_FROM_REQUEST = "cm";
    public static final int LINEAR_DIMENSION = 10;
    public static final String WEIGHT_UNIT_FROM_REQUEST = "kg";
    public static final String CHECKED_LUGGAGE_TYPE_DESCRIPTION = "checked";
    public static final int WEIGHT = 10;
    public static final String INVALID_UNIT = "invalid";
    public static final int WEIGHT_UNIT_CONVERTED = 10000;
    public static final int LINEAR_DIMENSION_CONVERTED = 100;
    public static final String VALID_LUGGAGE_TYPE = "checked";

    @Test
    public void givenValidRequest_whenGetDomainLuggage_shouldReturnLuggage() throws Exception {
        RegisterLuggageRequest registerLuggageRequest = new RegisterLuggageRequest(DIMENSION_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_FROM_REQUEST,
                WEIGHT,
                CHECKED_LUGGAGE_TYPE_DESCRIPTION);

        RegisterLuggageRequestAssembler registerLuggageRequestAssembler = new RegisterLuggageRequestAssembler();
        Luggage actualLuggage = registerLuggageRequestAssembler.getDomainLuggage(registerLuggageRequest);

        Luggage expectedLuggage = new Luggage(actualLuggage.getLuggageHash(),
                LINEAR_DIMENSION_CONVERTED,
                WEIGHT_UNIT_CONVERTED,
                VALID_LUGGAGE_TYPE);
        assertEquals(expectedLuggage, actualLuggage);
    }

    @Test(expected = NoSuchStrategyException.class)
    public void givenInvalidWeightUnit_whenGetDomainLuggage_shouldThrowMissingFieldException() throws Exception {
        RegisterLuggageRequest registerLuggageRequest = new RegisterLuggageRequest(DIMENSION_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                INVALID_UNIT,
                WEIGHT,
                CHECKED_LUGGAGE_TYPE_DESCRIPTION);

        RegisterLuggageRequestAssembler registerLuggageRequestAssembler = new RegisterLuggageRequestAssembler();
        registerLuggageRequestAssembler.getDomainLuggage(registerLuggageRequest);
    }

    @Test(expected = NoSuchStrategyException.class)
    public void givenInvalidDimensionUnit_whenGetDomainLuggage_shouldThrowMissingFieldException() throws Exception {
        RegisterLuggageRequest registerLuggageRequest = new RegisterLuggageRequest(INVALID_UNIT,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_FROM_REQUEST,
                WEIGHT,
                CHECKED_LUGGAGE_TYPE_DESCRIPTION);

        RegisterLuggageRequestAssembler registerLuggageRequestAssembler = new RegisterLuggageRequestAssembler();
        registerLuggageRequestAssembler.getDomainLuggage(registerLuggageRequest);
    }
}