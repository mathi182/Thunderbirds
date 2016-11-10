package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.MissingFieldException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions.IllegalFieldWebException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class RegisterBaggageRequestAssemblerTest {
    public static final String DIMENSION_UNIT_FROM_REQUEST = "cm";
    public static final int LINEAR_DIMENSION = 10;
    public static final String WEIGHT_UNIT_FROM_REQUEST = "kg";
    public static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    public static final int WEIGHT = 10;
    public static final String INVALID_UNIT = "invalid";

    @Test
    public void givenValidRequest_whenGetDomainBaggage_shouldReturnCheckedBaggageEconomy() throws Exception {
        RegisterBaggageRequest registerBaggageRequest = new RegisterBaggageRequest(DIMENSION_UNIT_FROM_REQUEST,
                                                                                   LINEAR_DIMENSION,
                                                                                   WEIGHT_UNIT_FROM_REQUEST,
                                                                                   WEIGHT,
                                                                                   CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageRequestAssembler registerBaggageRequestAssembler = new RegisterBaggageRequestAssembler();
        Baggage baggage = registerBaggageRequestAssembler.getDomainBaggage(registerBaggageRequest);

        assertThat(baggage, instanceOf(Baggage.class));
    }

    @Test(expected = MissingFieldException.class)
    public void givenMissingField_whenGetDomainBaggage_shouldThrowMissingFieldException() throws Exception {
        RegisterBaggageRequest registerBaggageRequest = new RegisterBaggageRequest(DIMENSION_UNIT_FROM_REQUEST,
                                                                                   LINEAR_DIMENSION,
                                                                                   WEIGHT_UNIT_FROM_REQUEST,
                                                                                   null,
                                                                                   null);

        RegisterBaggageRequestAssembler registerBaggageRequestAssembler = new RegisterBaggageRequestAssembler();
        registerBaggageRequestAssembler.getDomainBaggage(registerBaggageRequest);
    }
}