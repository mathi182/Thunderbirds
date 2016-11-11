package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.regex.Pattern;

import static ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext.EXISTENT_BOARDING_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.eclipse.jetty.http.HttpStatus.Code.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BaggageResourceRestTest {
    private static final String DIMENSION_UNIT_DESCRIPTION = "CM";
    private static final int LINEAR_DIMENSION = 10;
    private static final String WEIGHT_UNIT_DESCRIPTION = "KG";
    private static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    private static final int WEIGHT = 10;
    private static final int INVALID_WEIGHT = 400;
    private static final String INVALID_UNIT = "invalid_unit";
    private static final String VALID_PASSENGER_HASH = EXISTENT_BOARDING_PASSENGER_HASH.toString();

    @Test
    public void givenAValidBaggageAndExistentPassenger_whenRegisteringValidBaggage_shouldRegisterBaggage() {
        RegisterBaggageRequest registerBaggageRequest = new RegisterBaggageRequest(DIMENSION_UNIT_DESCRIPTION,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_DESCRIPTION,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        Response response =
                givenBaseRequest()
                        .body(registerBaggageRequest)
                        .when()
                        .post("/passengers/" + VALID_PASSENGER_HASH + "/baggages")
                        .then()
                        .statusCode(CREATED.getCode())
                        .extract()
                        .response();

        Boolean locationValidity = isLocationValid(response.getHeader("Location"), VALID_PASSENGER_HASH);
        assertTrue(locationValidity);

        Boolean allowed = response.path("allowed");
        assertTrue(allowed);

        String refusationReason = response.path("refusation_reason");
        assertNull(refusationReason);
    }

    private boolean isLocationValid(String location, String passengerHash) {
        String baseUrl = buildUrl("/passengers/" + passengerHash + "/baggages/");
        baseUrl = baseUrl.replace("/", "\\/");
        Pattern pattern = Pattern.compile(baseUrl + "\\d+$");

        return pattern.matcher(location).matches();
    }

    @Test
    public void givenAnInvalidWeightBaggage_whenRegisteringBaggage_shouldReturnOk() {
        RegisterBaggageRequest registerBagageRequest = new RegisterBaggageRequest(DIMENSION_UNIT_DESCRIPTION,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_DESCRIPTION,
                INVALID_WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        Response response =
                givenBaseRequest()
                        .body(registerBagageRequest)
                        .when()
                        .post("/passengers/" + VALID_PASSENGER_HASH + "/baggages")
                        .then()
                        .statusCode(OK.getCode())
                        .extract()
                        .response();

        Boolean allowed = response.path("allowed");
        assertFalse(allowed);
        String refusationReason = response.path("refusation_reason");
        assertNotNull(refusationReason);
    }

    @Test
    public void givenAnInvalidWeightUnitBaggage_whenRegisteringBaggage_shouldReturnBadRequest() {
        RegisterBaggageRequest registerBagageRequest = new RegisterBaggageRequest(DIMENSION_UNIT_DESCRIPTION,
                LINEAR_DIMENSION,
                INVALID_UNIT,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        givenBaseRequest()
                .body(registerBagageRequest)
                .when()
                .post("/passengers/" + VALID_PASSENGER_HASH + "/baggages")
                .then()
                .statusCode(BAD_REQUEST.getCode());
    }
}
