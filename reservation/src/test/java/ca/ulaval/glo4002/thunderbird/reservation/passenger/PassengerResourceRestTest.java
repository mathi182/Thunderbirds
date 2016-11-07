package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.RestTestSuite;
import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import static org.hamcrest.Matchers.equalTo;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;


public class PassengerResourceRestTest {
    private static final UUID RANDOM_UUID = UUID.randomUUID();
    private static final String SERVICE_ADDRESS = "http://127.0.0.1:" + RestTestSuite.TEST_SERVER_PORT;
    private static final String PASSENGER_PATH_FORMAT = "/reservations/%1s";
    private static final int EXISTING_RESERVATION_NUMBER = DevContext.EXISTENT_RESERVATION_NUMBER;

    private static PassengerTestDTO existingPassenger;

    @BeforeClass
    public static void getPassengerInformation() {
        String url = SERVICE_ADDRESS + String.format(PASSENGER_PATH_FORMAT, EXISTING_RESERVATION_NUMBER);
        ClientResponse response = getResource(url);
        ReservationTestDTO reservationTest = response.getEntity(ReservationTestDTO.class);

        existingPassenger = reservationTest.passengers.get(0);
    }

    @Test
    public void givenRandomPassengerHash_WhenAskingForPassenger_ShouldReturnNotFound() {
        givenBaseRequest()
                .when()
                .get("/passenger/{passenger_UUID}", RANDOM_UUID.toString())
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenExistingPassengerHash_WhenAskingForPassenger_ShouldReturnOK(){
        givenBaseRequest()
                .when()
                .get("/passenger/{passenger_UUID}", existingPassenger.passengerHash)
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    public void givenExistingPassengerHash_WhenAskingForPassenger_InformationShouldBeCorrect(){
        givenBaseRequest()
                .when()
                .get("/passenger/{passenger_UUID}", existingPassenger.passengerHash)
                .then()
                .body("first_name",equalTo(existingPassenger.first_name))
                .body("last_name",equalTo(existingPassenger.last_name))
                .body("passport_number",equalTo(existingPassenger.passport_number))
                .body("seat_class",equalTo(existingPassenger.seatClass));
    }

    private static ClientResponse getResource(String url) {
        Client client = Client.create();
        WebResource resource = client.resource(url);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        return response;
    }

}