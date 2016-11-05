package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import org.junit.Ignore;
import org.junit.Test;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.*;


public class PassengerResourceRestTest {
    @Test
    public void givenNoPassengerNumber_WhenAskingForPassenger_ShouldReturnBadRequest(){
        givenBaseRequest()
                .when()
                .get("/passenger/test")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode());
    }

}