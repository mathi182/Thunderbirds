package fixtures.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.event.EventsResource;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResource;
import io.restassured.response.Response;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class RestReservationFixture extends ReservationBaseRestFixture {
    private static final int RESERVATION_NUMBER = 1;
    private static final String RESERVATION_DATE = "2016-10-15";
    private static final String FIRST_NAME = "Alice";
    private static final String LAST_NAME = "Trembley";
    private static final int AGE = 21;
    private static final String PASSPORT_NUMBER = "1234";
    private static final String SEAT_CLASS = "economy";
    public static final int FIRST_PASSENGER = 0;
    public static final String PASSENGER_HASH_JSON_PROPERTY = "passenger_hash";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    public UUID givenAPassengerWithAReservation(String flightNumber, Instant flightDate) {
        createReservation(flightNumber, flightDate);
        return getReservationPassengerHash(RESERVATION_NUMBER);
    }

    private void createReservation(String flightNumber, Instant flightDate) {
        UriBuilder uriBuilder = UriBuilder.fromUri(EventsResource.PATH);
        String createReservationPath = uriBuilder.path(EventsResource.RESERVATION_CREATED).toString();

        Map<String, Object> body = getReservationJsonAsMap(flightNumber, flightDate);
        Response response = givenRequest().body(body)
                .when().post(createReservationPath);
        int i = 0;
    }

    private Map<String, Object> getReservationJsonAsMap(String flightNumber, Instant flightDate) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("reservation_number", RESERVATION_NUMBER);
        jsonAsMap.put("reservation_date", RESERVATION_DATE);
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

    private UUID getReservationPassengerHash(int reservationNumber) {
        UriBuilder uriBuilder = UriBuilder.fromPath(ReservationsResource.PATH);
        String integerNumberString = Integer.toString(reservationNumber);
        URI uri = uriBuilder.path(integerNumberString).build();

        Response response = givenRequest()
                .when().get(uri);

        return UUID.randomUUID();
    }
}