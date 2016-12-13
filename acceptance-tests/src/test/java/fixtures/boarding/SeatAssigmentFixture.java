package fixtures.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationsResource;
import io.restassured.response.Response;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SeatAssigmentFixture extends BoardingBaseRestFixture {
    private Response currentRequest;

    public void whenAssignSeat(UUID passengerHash, String mode) {
        URI uri = UriBuilder.fromPath(SeatAssignationsResource.PATH).build();
        Map<String, Object> body = getJsonAsMap(passengerHash, mode);

        currentRequest = givenRequest().body(body)
                .when().post(uri);
    }

    private Map<String, Object> getJsonAsMap(UUID passengerHash, String mode) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("passenger_hash", passengerHash.toString());
        jsonAsMap.put("mode", mode);
        return jsonAsMap;
    }
}