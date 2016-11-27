package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;

import static ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext.EXISTENT_BOARDING_PASSENGER;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.OK;
import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;
import static org.eclipse.jetty.http.HttpStatus.Code.CREATED;
import static org.eclipse.jetty.http.HttpStatus.Code.NOT_FOUND;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class BaggageResourceRestTest {
    private static final String CM_UNIT_FROM_REQUEST = "cm";
    private static final int LINEAR_DIMENSION = 10;
    private static final String KG_UNIT_FROM_REQUEST = "kg";
    private static final String CHECKED_BAGGAGE_TYPE_DESCRIPTION = "checked";
    private static final int WEIGHT = 10;
    private static final int INVALID_WEIGHT = 4000;
    private static final String INVALID_UNIT = "invalid_unit";
    private static final UUID VALID_PASSENGER_HASH = EXISTENT_BOARDING_PASSENGER.getHash();
    private static final UUID INVALID_PASSENGER_UUID = UUID.randomUUID();

    private static final String UUIDRegex = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";

    @Test
    public void givenAValidBaggageAndExistentPassenger_whenRegisteringValidBaggage_shouldRegisterBaggage() {
        Map<String, Object> registerBaggageBody = createRegisterBaggageBody(CM_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                KG_UNIT_FROM_REQUEST,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        Response response = givenBaseRequest()
                .body(registerBaggageBody)
                .when().post(String.format("/passengers/%s/baggages", VALID_PASSENGER_HASH))
                .then()
                .statusCode(CREATED.getCode())
                .extract().response();

        Boolean locationValidity = isLocationValid(response.getHeader("Location"), VALID_PASSENGER_HASH);
        assertTrue(locationValidity);
        Boolean allowed = response.path("allowed");
        assertTrue(allowed);
        String deniedReason = response.path("refusation_reason");
        assertNull(deniedReason);
    }

    @Test
    public void givenAValidPassengerWithBaggages_whenGettingBaggagesList_shouldReturnBaggagesList() throws
            JsonProcessingException {
        BaggagesListDTO baggagesListDTO = buildExistentBoardingPassengerBaggagesListDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResponse = objectMapper.writeValueAsString(baggagesListDTO);

        givenBaseRequest()
                .when()
                .get("/passengers/" + VALID_PASSENGER_HASH + "/baggages")
                .then()
                .statusCode(OK.getStatusCode())
                .body(equalTo(expectedResponse));
    }

    private BaggagesListDTO buildExistentBoardingPassengerBaggagesListDTO() {
        BaggagesListAssembler baggagesListAssembler = new BaggagesListAssembler();
        return baggagesListAssembler.toDTO(EXISTENT_BOARDING_PASSENGER.getBaggages());
    }

    @Test
    public void givenAnInvalidPassenger_whenGettingBaggagesList_shouldGetNotFound() {
        givenBaseRequest()
                .when()
                .get("/passengers/" + INVALID_PASSENGER_UUID + "/baggages")
                .then()
                .statusCode(NOT_FOUND.getCode());
    }

    private boolean isLocationValid(String location, UUID passengerHash) {
        String baseUrl = buildUrl("/passengers/" + passengerHash.toString() + "/baggages/");
        baseUrl = baseUrl.replace("/", "\\/");
        Pattern pattern = Pattern.compile(baseUrl + UUIDRegex);
        return pattern.matcher(location).matches();
    }

    @Test
    public void givenAnInvalidWeightBaggage_whenRegisteringBaggage_shouldReturnOk() {
        Map<String, Object> registerBagageBody = createRegisterBaggageBody(CM_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                KG_UNIT_FROM_REQUEST,
                INVALID_WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        Response response = givenBaseRequest()
                .body(registerBagageBody)
                .when().post(String.format("/passengers/%s/baggages", VALID_PASSENGER_HASH))
                .then().statusCode(OK.getStatusCode())
                .extract().response();

        Boolean allowed = response.path("allowed");
        assertFalse(allowed);
        String deniedReason = response.path("refusation_reason");
        assertNotNull(deniedReason);
    }

    @Test
    public void givenAnInvalidWeightUnitBaggage_whenRegisteringBaggage_shouldReturnBadRequest() {
        Map<String, Object> registerBaggageBody = createRegisterBaggageBody(CM_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                INVALID_UNIT,
                WEIGHT,
                CHECKED_BAGGAGE_TYPE_DESCRIPTION);

        givenBaseRequest()
                .body(registerBaggageBody)
                .when().post(String.format("/passengers/%s/baggages", VALID_PASSENGER_HASH))
                .then().statusCode(BAD_REQUEST.getCode());
    }

    private Map<String, Object> createRegisterBaggageBody(String linearDimensionUnit,
                                                          int linearDimension,
                                                          String weightUnit,
                                                          int weight,
                                                          String baggageType) {
        Map<String, Object> registerBaggageBody = new HashMap<>();
        registerBaggageBody.put("linear_dimension_unit", linearDimensionUnit);
        registerBaggageBody.put("linear_dimension", linearDimension);
        registerBaggageBody.put("weight", weight);
        registerBaggageBody.put("weight_unit", weightUnit);
        registerBaggageBody.put("type", baggageType);

        return registerBaggageBody;
    }
}