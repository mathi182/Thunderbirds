package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.rest.RegexMatcher;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import static ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext.EXISTENT_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static junit.framework.TestCase.*;
import static org.eclipse.jetty.http.HttpStatus.Code.*;
import static org.junit.Assert.assertNull;

public class BaggageResourceRestTest {
    private static final String DIMENSION_UNIT_DESCRIPTION = "CM";
    private static final int LINEAR_DIMENSION = 10;
    private static final String WEIGHT_UNIT_DESCRIPTION = "KG";
    private static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    private static final int WEIGHT = 10;
    private static final int INVALID_WEIGHT = 400;
    private static final String INVALID_UNIT = "invalid_unit";
    private static final String VALID_PASSENGER_HASH = EXISTENT_PASSENGER_HASH.toString();

    @Ignore
    @Test
    public void givenAValidBaggageAndExistentPassenger_whenRegisteringValidBaggage_shouldRegisterBaggage() {
        RegisterBaggageRequest registerBaggageRequest = new RegisterBaggageRequest(DIMENSION_UNIT_DESCRIPTION,
                LINEAR_DIMENSION,
                WEIGHT_UNIT_DESCRIPTION,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        String locationRegex = createRegexLocationExpected(VALID_PASSENGER_HASH);
        RegexMatcher locationMatcher = RegexMatcher.matches(locationRegex);
        Response response =
                givenBaseRequest()
                        .body(registerBaggageRequest)
                        .when()
                        .post("/passengers/" + VALID_PASSENGER_HASH + "/baggages")
                        .then()
                        .statusCode(CREATED.getCode())
                        .header("Location", locationMatcher)
                        .extract()
                        .response();

        Boolean allowed = response.path("allowed");
        assertTrue(allowed);
        String refusationReason = response.path("refusation_reason");
        assertNull(refusationReason);
    }

    private String createRegexLocationExpected(String passengerHash) {
        String regex = buildUrl("/passengers/" + passengerHash + "/baggages/" + "\\d");
        return regex;
    }

    @Ignore
    @Test
    public void givenAnInvalidWeightBaggage_whenRegisteringBaggage_shouldReturnOk() {
        //TODO: utiliser un passengerHash d'un passenger existant quand la ressource fera cette validation
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

    @Ignore
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
