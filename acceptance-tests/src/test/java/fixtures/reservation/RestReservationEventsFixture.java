package fixtures.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.event.EventsResource;
import io.restassured.response.Response;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class RestReservationEventsFixture extends ReservationBaseRestFixture {
    private static final int RESERVATION_NUMBER = 1;
    private static final Instant RESERVATION_DATE = Instant.now();
    private static final String RESERVATION_CONFIRMATION = "";
    private static final String PAYMENT_LOCATION = "";
    private static final String FIRST_NAME = "Alice";
    private static final String LAST_NAME = "Trembley";
    private static final int AGE = 21;
    private static final String PASSPORT_NUMBER = "1234";
    private static final String SEAT_CLASS = "economy";

    private Response currentRequest;

    public void givenAReservation(String flightNumber, Instant flightDate) {
        String path = EventsResource.PATH;
        URI uri = UriBuilder.fromPath(path).build();
        Map<String, Object> body = getReservationJsonAsMap(flightNumber, flightDate);

        currentRequest = givenRequest().body(body)
                .when().post(uri);
    }

    private Map<String, Object> getReservationJsonAsMap(String flightNumber, Instant flightDate) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("reservation_number", RESERVATION_NUMBER);
        jsonAsMap.put("reservation_date", ISO_INSTANT.format(RESERVATION_DATE));
        jsonAsMap.put("reservation_confirmation", RESERVATION_CONFIRMATION);
        jsonAsMap.put("flight_number", flightNumber);
        jsonAsMap.put("flight_date", ISO_INSTANT.format(flightDate));
        jsonAsMap.put("payment_location", PAYMENT_LOCATION);
        jsonAsMap.put("passengers", getPassengerJsonAsMap());
        return jsonAsMap;
    }

    private Map<String, Object> getPassengerJsonAsMap() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", FIRST_NAME);
        jsonAsMap.put("last_name", LAST_NAME);
        jsonAsMap.put("age", AGE);
        jsonAsMap.put("passport_number", PASSPORT_NUMBER);
        jsonAsMap.put("seat_class", SEAT_CLASS);
        return jsonAsMap;
    }
}