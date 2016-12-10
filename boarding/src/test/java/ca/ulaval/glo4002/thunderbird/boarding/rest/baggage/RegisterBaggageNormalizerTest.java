package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterBaggageAssemblerTest {
    private static final String DIMENSION_UNIT_FROM_REQUEST = "cm";
    private static final int LINEAR_DIMENSION = 10;
    private static final String WEIGHT_UNIT_FROM_REQUEST = "kg";
    private static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    private static final int WEIGHT = 10;
    private static final String INVALID_UNIT = "invalid";
    private static final double WEIGHT_UNIT_CONVERTED = 10000;
    private static final double LINEAR_DIMENSION_CONVERTED = 100;
    private static final double UNIT_DELTA = 0.001;
    private static final Speciality CLASSIC_SPECIALITY = new Classic();

    @Test
    public void givenValidRequest_whenGetDomainBaggage_shouldReturnBaggage() {
        RegisterBaggageDTO registerBaggage = new RegisterBaggageDTO(DIMENSION_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_FROM_REQUEST,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageAssembler registerBaggageAssembler = new RegisterBaggageAssembler();
        Baggage actualBaggage = registerBaggageAssembler.getDomainBaggage(registerBaggage);
        double actualLength = actualBaggage.getDimension().toMillimeters();
        double actualMass = actualBaggage.getWeight().toGrams();

        assertEquals(LINEAR_DIMENSION_CONVERTED, actualLength, UNIT_DELTA);
        assertEquals(WEIGHT_UNIT_CONVERTED, actualMass, UNIT_DELTA);
        assertTrue(actualBaggage.hasSpeciality(CLASSIC_SPECIALITY));
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