package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;
import static org.eclipse.jetty.http.HttpStatus.Code.CREATED;
import static org.eclipse.jetty.http.HttpStatus.Code.OK;
import static org.junit.Assert.*;

public class BagageRessourceRestTest {
    public static final String CM_UNIT_FROM_REQUEST = "cm";
    public static final int LINEAR_DIMENSION = 10;
    public static final String KG_UNIT_FROM_REQUEST = "kg";
    public static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    public static final int WEIGHT = 10;
    public static final int INVALID_WEIGHT = 4000;
    public static final String INVALID_UNIT = "invalid_unit";

    @Test
    public void givenAValidBaggageAndExistentPassenger_whenRegisteringValidBaggage_shouldRegisterBaggage() {
        //TODO: utiliser un passengerHash d'un passenger existant quand la ressource fera cette validation
        RegisterBaggageRequest registerBagageRequest = new RegisterBaggageRequest(CM_UNIT_FROM_REQUEST,
                                                                                  LINEAR_DIMENSION,
                KG_UNIT_FROM_REQUEST,
                                                                                  WEIGHT,
                                                                                  CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        String locationExpected = createLocationExpected("12345","baggageHash");
        Response response =
                givenBaseRequest()
                .body(registerBagageRequest)
                .when()
                .post("/passengers/12345/baggages")
                .then()
                .statusCode(CREATED.getCode())
                .header("Location", buildUrl(locationExpected))
                .extract()
                .response();

        Boolean allowed = response.path("allowed");
        assertTrue(allowed);
        String refusationReason = response.path("refusation_reason");
        assertNull(refusationReason);
    }

    private String createLocationExpected(String passengerHash, String bagageHash) {
        return "/passengers/"+passengerHash+ "/baggages/" +bagageHash;
    }

    @Test
    @Ignore // delete this when baggage validates its weight and dimension again
    public void givenAnInvalidWeightBaggage_whenRegisteringBaggage_shouldReturnOk() {
        //TODO: utiliser un passengerHash d'un passenger existant quand la ressource fera cette validation
        RegisterBaggageRequest registerBagageRequest = new RegisterBaggageRequest(CM_UNIT_FROM_REQUEST,
                                                                                  LINEAR_DIMENSION,
                                                                                  KG_UNIT_FROM_REQUEST,
                                                                                  INVALID_WEIGHT,
                                                                                  CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        Response response =
                givenBaseRequest()
                .body(registerBagageRequest)
                .when()
                .post("/passengers/123456/baggages")
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
        RegisterBaggageRequest registerBagageRequest = new RegisterBaggageRequest(CM_UNIT_FROM_REQUEST,
                                                                                  LINEAR_DIMENSION,
                                                                                  INVALID_UNIT,
                                                                                  WEIGHT,
                                                                                  CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        givenBaseRequest()
                .body(registerBagageRequest)
                .when()
                .post("/passengers/123456/baggages")
                .then()
                .statusCode(BAD_REQUEST.getCode());
    }
}
