package ca.ulaval.glo4002.thunderbird.reservation.event;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EventsResourceRestTest {

    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final String FLIGHT_DATE = "2016-10-15T11:41:00Z";
    private static final ArrayList<Passenger> PASSENGERS = new ArrayList<>();
    private static final String RESERVATION_CREATED_PATH = "/reservations/" + RESERVATION_NUMBER;

    private static final String FIRST_NAME = "Alexis";
    private static final String LAST_NAME = "Lessard";
    private static final int AGE = 18;
    private static final String PASSPORT_NUMBER = "testo";
    private static final String SEAT_CLASS = "economy";

    private Reservation reservation;

    @Before
    public void setUp() {
        reservation = new Reservation(RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                PASSENGERS);
    }

    @Test
    public void givenValidReservation_whenCreatingReservation_shouldCreateReservation() {
        givenBaseRequest().contentType("application/json")
                .body(generateReservationRequest())
                .when()
                .post("/events/reservation-created")
                .then()
                .statusCode(CREATED.getStatusCode())
                .header("Location", buildUrl(RESERVATION_CREATED_PATH));
    }

    private String generateReservationRequest() {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> reservationMap = new LinkedHashMap<>();
        Map<String, Object> passengerMap = new LinkedHashMap<>();

        //TODO put the creation of both maps and lists in private functions
        reservationMap.put("reservation_number", RESERVATION_NUMBER);
        reservationMap.put("reservation_date", RESERVATION_DATE);
        reservationMap.put("reservation_confirmation", RESERVATION_CONFIRMATION);
        reservationMap.put("flight_number", FLIGHT_NUMBER);
        reservationMap.put("flight_date", FLIGHT_DATE);
        reservationMap.put("payment_location", PAYMENT_LOCATION);

        passengerMap.put("first_name", FIRST_NAME);
        passengerMap.put("last_name", LAST_NAME);
        passengerMap.put("age", AGE);
        passengerMap.put("passport_number", PASSPORT_NUMBER);
        passengerMap.put("seat_class", SEAT_CLASS);

        List<Map> passengersList = new ArrayList<>();
        passengersList.add(passengerMap);

        reservationMap.put("passengers",passengersList);

        String response = "no json inputed";

        try {
            response = mapper.writeValueAsString(reservationMap);
        }
        catch (Exception e){
            fail("Problem with ReservationJSON");
        }

        return response;
    }
}