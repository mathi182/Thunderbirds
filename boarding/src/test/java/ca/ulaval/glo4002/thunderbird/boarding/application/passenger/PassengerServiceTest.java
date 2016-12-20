package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestSuite;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext.EXISTENT_RESERVATION_NUMBER;

public class PassengerServiceTest {

    @Test
    public void givenAValidPassengerHashOnReservation_whenFetchingPassenger_shouldReturnPassenger(){
        UUID validUUID = getValidPassengerUUID();
        PassengerService passengerService = new PassengerService();

        Passenger passenger = passengerService.fetchPassenger(validUUID);

        UUID expectedUUID = validUUID;
        UUID actualUUID = passenger.getHash();
        assertEquals(expectedUUID,actualUUID);
    }

    private UUID getValidPassengerUUID(){
        Response response;
        ArrayList<HashMap<String,String>> arrayList = givenBaseRequest().
                when().get("/reservations/{reservation_number}", EXISTENT_RESERVATION_NUMBER)
                .then().extract().path("passengers");
        String uuidString = arrayList.get(0).get("passenger_hash");
        return UUID.fromString(uuidString);
    }

    private RequestSpecification givenBaseRequest() {
        int httpPort = RestTestSuite.TEST_SERVER_PORT_RESERVATION;
        return given()
                .accept(ContentType.JSON)
                .port(httpPort)
                .contentType(ContentType.JSON);
    }
}
