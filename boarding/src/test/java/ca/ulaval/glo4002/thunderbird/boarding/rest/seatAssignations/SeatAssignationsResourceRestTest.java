package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignations;
import ca.ulaval.glo4002.thunderbird.boarding.rest.SeatAssignationsResource;
import org.junit.Ignore;
import org.junit.Test;

import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.EXISTANT_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class SeatAssignationsResourceRestTest {
    @Test
    public void givenInvalidPassengerHash_whenAssigningSeat_shouldReturnNotFound() {
        SeatAssignations seatAssignations = new SeatAssignations("passengerHash", "mode");

        givenBaseRequest()
                .body(seatAssignations)
                .when()
                .post(SeatAssignationsResource.PATH)
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    //TODO:
    @Test
    @Ignore
    public void whenAssigningASeat_shouldReturnCreated() {
        SeatAssignations seatAssignations = new SeatAssignations(EXISTANT_PASSENGER_HASH, "mode");

        givenBaseRequest()
                .body(seatAssignations)
                .when()
                .post(SeatAssignationsResource.PATH)
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    //TODO:
    @Test
    @Ignore
    public void whenAssiningASeat_locationShouldBeAdequate() throws Exception {
    }
}