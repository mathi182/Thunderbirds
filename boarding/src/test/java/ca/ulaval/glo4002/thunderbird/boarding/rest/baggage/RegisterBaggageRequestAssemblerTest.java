package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.CheckedBaggageEconomy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.MissingFieldException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class RegisterBaggageRequestAssemblerTest {
    public static final String DIMENSION_UNIT_DESCRIPTION = "cm";
    public static final int LINEAR_DIMENSION = 10;
    public static final String WEIGHT_UNIT_DESCRIPTION = "kg";
    public static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    public static final int WEIGHT = 10;

    @Test
    public void givenValidRegisterBaggageRequest_whenGetDomainBaggage_shouldReturnCheckedBaggageEconomy() throws Exception {
        RegisterBaggageRequest registerBaggageRequest = new RegisterBaggageRequest(DIMENSION_UNIT_DESCRIPTION,
                                                                                   LINEAR_DIMENSION,
                                                                                   WEIGHT_UNIT_DESCRIPTION,
                                                                                   WEIGHT,
                                                                                   CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        RegisterBaggageRequestAssembler registerBaggageRequestAssembler = new RegisterBaggageRequestAssembler();
        Baggage baggage = registerBaggageRequestAssembler.getDomainBaggage(registerBaggageRequest);

        assertThat(baggage, instanceOf(CheckedBaggageEconomy.class));
    }

    @Test(expected = MissingFieldException.class)
    public void givenMissingFieldRegisterBaggageRequest_whenGetDomainBaggage_shouldThrowMissingFieldException() throws Exception {
        RegisterBaggageRequest registerBaggageRequest = new RegisterBaggageRequest(DIMENSION_UNIT_DESCRIPTION,
                                                                                   LINEAR_DIMENSION,
                                                                                   WEIGHT_UNIT_DESCRIPTION,
                                                                                   null,
                                                                                   null);

        RegisterBaggageRequestAssembler registerBaggageRequestAssembler = new RegisterBaggageRequestAssembler();
        registerBaggageRequestAssembler.getDomainBaggage(registerBaggageRequest);
    }
}