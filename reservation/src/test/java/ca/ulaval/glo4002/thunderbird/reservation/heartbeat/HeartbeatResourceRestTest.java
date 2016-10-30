package ca.ulaval.glo4002.thunderbird.reservation.heartbeat;

import ca.ulaval.glo4002.thunderbird.reservation.RestTestBase;
import org.junit.Test;

import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;

public class HeartbeatResourceRestTest extends RestTestBase {

    private final static String PARAM_NAME = "token";
    private final static String TOKEN = "A_TOKEN";

    @Test
    public void givenAToken_whenGetHeartbeat_shouldReturnThisToken() {
        givenBaseRequest()
                    .param(PARAM_NAME, TOKEN)
                .when()
                    .get("/heartbeat")
                .then()
                    .statusCode(OK.getStatusCode())
                    .body("token", equalTo(TOKEN));
    }

}