package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.RestTestSuite;
import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;

public class PassengersResourceRestTest {
    private static final UUID RANDOM_UUID = UUID.randomUUID();
    private static final String SERVICE_ADDRESS = "http://127.0.0.1:" + RestTestSuite.TEST_SERVER_PORT;
    private static final String PASSENGER_PATH_FORMAT = "/reservations/%1s";
    private static final String PASSENGER_SERVICE_ADDRESS = PassengersResource.PATH;
    private static final String PASSENGER_REST_FORMAT = PASSENGER_SERVICE_ADDRESS + "{passenger_UUID}";
    private static final int EXISTENT_RESERVATION_NUMBER = DevContext.EXISTENT_RESERVATION_NUMBER;

    private static Map<String, Object> existingPassenger;

    @BeforeClass
    public static void getPassengerInformation() {
        String url = SERVICE_ADDRESS + String.format(PASSENGER_PATH_FORMAT, EXISTENT_RESERVATION_NUMBER);

        Map<String, Object> existingReservation = getResource(url).getEntity(Map.class);
        ArrayList<Object> existingPassengers = (ArrayList<Object>) existingReservation.get("passengers");
        existingPassenger = (Map<String, Object>) existingPassengers.get(0);
    }

    private static ClientResponse getResource(String url) {
        return Client
                .create()
                .resource(url)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
    }

    @Test
    public void givenRandomPassengerHash_whenAskingForPassenger_shouldReturnNotFound() {
        givenBaseRequest()
                .when()
                .get(PASSENGER_REST_FORMAT , RANDOM_UUID.toString())
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenExistingPassengerHash_whenAskingForPassenger_shouldReturnExistentPassenger() {
        givenBaseRequest()
                .when()
                .get(PASSENGER_REST_FORMAT, existingPassenger.get("passenger_hash"))
                .then()
                .statusCode(OK.getStatusCode())
                .body("passenger_hash", equalTo(existingPassenger.get("passenger_hash")))
                .body("first_name", equalTo(existingPassenger.get("first_name")))
                .body("last_name", equalTo(existingPassenger.get("last_name")))
                .body("passport_number", equalTo(existingPassenger.get("passport_number")))
                .body("seat_class", equalTo(existingPassenger.get("seat_class")))
                .body("child",equalTo(false));
    }
}