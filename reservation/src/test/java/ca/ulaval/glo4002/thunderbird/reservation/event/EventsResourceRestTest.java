package ca.ulaval.glo4002.thunderbird.reservation.event;

import org.junit.Before;
import org.junit.Test;
import java.util.*;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResource;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.CREATED;

public class EventsResourceRestTest {

    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final String FLIGHT_DATE = "2016-10-15T11:41:00Z";

    private static final String FIRST_NAME = "Alexis";
    private static final String LAST_NAME = "Lessard";
    private static final int AGE = 18;
    private static final String PASSPORT_NUMBER = "testo";
    private static final String SEAT_CLASS = "economy";

    private String createReservationPath;

    @Before
    public void setUp(){
        UriBuilder uriBuilderRequest = UriBuilder.fromUri(EventsResource.PATH);
        createReservationPath = uriBuilderRequest
                .path(EventsResource.RESERVATION_CREATED)
                .toString();
    }

    @Test
    public void givenValidReservation_whenCreatingReservation_shouldCreateReservation() {
        String reservationNumberString = Integer.toString(RESERVATION_NUMBER);
        String locationExpected = createLocationExpected(reservationNumberString);

        givenBaseRequest()
                .body(generateReservationMap())
                .when()
                    .post(createReservationPath)
                .then()
                    .statusCode(CREATED.getStatusCode())
                    .header("Location", buildUrl(locationExpected));
    }

    private String createLocationExpected(String reservationNumber) {
        UriBuilder uriBuilder = UriBuilder.fromUri(ReservationsResource.PATH);
        return uriBuilder.path(reservationNumber).toString();
    }

    private Map generateReservationMap(){
        Map<String, Object> reservationMap = new LinkedHashMap<>();

        reservationMap.put("reservation_number", RESERVATION_NUMBER);
        reservationMap.put("reservation_date", RESERVATION_DATE);
        reservationMap.put("reservation_confirmation", RESERVATION_CONFIRMATION);
        reservationMap.put("flight_number", FLIGHT_NUMBER);
        reservationMap.put("flight_date", FLIGHT_DATE);
        reservationMap.put("payment_location", PAYMENT_LOCATION);

        reservationMap.put("passengers", generatePassengerList());

        return  reservationMap;
    }

    private List generatePassengerList(){
        List<Map> passengersList = new ArrayList<>();
        passengersList.add(generatePassengerMap());

        return passengersList;
    }

    private Map generatePassengerMap() {
        Map<String, Object> passengerMap = new LinkedHashMap<>();

        passengerMap.put("first_name", FIRST_NAME);
        passengerMap.put("last_name", LAST_NAME);
        passengerMap.put("age", AGE);
        passengerMap.put("passport_number", PASSPORT_NUMBER);
        passengerMap.put("seat_class", SEAT_CLASS);

        return passengerMap;
    }

}