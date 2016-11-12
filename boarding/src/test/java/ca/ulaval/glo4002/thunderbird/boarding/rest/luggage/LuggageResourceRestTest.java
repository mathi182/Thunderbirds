package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage;

import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.util.regex.Pattern;

import static ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext.EXISTENT_BOARDING_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static org.eclipse.jetty.http.HttpStatus.Code.*;
import static org.junit.Assert.*;

public class LuggageResourceRestTest {
    public static final String CM_UNIT_FROM_REQUEST = "cm";
    public static final int LINEAR_DIMENSION = 10;
    public static final String KG_UNIT_FROM_REQUEST = "kg";
    public static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    public static final int WEIGHT = 10;
    public static final int INVALID_WEIGHT = 4000;
    public static final String INVALID_UNIT = "invalid_unit";
    private static final String VALID_PASSENGER_HASH = EXISTENT_BOARDING_PASSENGER_HASH.toString();

    @Test
    public void givenAValidBaggageAndExistentPassenger_whenRegisteringValidBaggage_shouldRegisterBaggage() {
        RegisterLuggageRequest registerBagageRequest = new RegisterLuggageRequest(CM_UNIT_FROM_REQUEST,
                                                                                  LINEAR_DIMENSION,
                                                                                  KG_UNIT_FROM_REQUEST,
                                                                                  WEIGHT,
                                                                                  CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        Response response =
                givenBaseRequest()
                        .body(registerBagageRequest)
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
    @Ignore // delete this when luggage validates its weight and dimension again
    public void givenAnInvalidWeightBaggage_whenRegisteringBaggage_shouldReturnOk() {
        //TODO: utiliser un passengerHash d'un passenger existant quand la ressource fera cette validation
        RegisterLuggageRequest registerBagageRequest = new RegisterLuggageRequest(CM_UNIT_FROM_REQUEST,
                                                                                  LINEAR_DIMENSION,
                                                                                  KG_UNIT_FROM_REQUEST,
                                                                                  INVALID_WEIGHT,
                                                                                  CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        Response response =
                givenBaseRequest()
                .body(registerBagageRequest)
                .when()
                        .post("/passengers/" + VALID_PASSENGER_HASH + " / baggages")
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
        RegisterLuggageRequest registerBagageRequest = new RegisterLuggageRequest(CM_UNIT_FROM_REQUEST,
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
