package ca.ulaval.glo4002.thunderbird.reservation.event;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResource;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.CREATED;

public class EventsResourceTest {

    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final String FLIGHT_DATE = "2016-09-06T13:00:00Z";
    private static final ArrayList<Passenger> PASSENGERS = new ArrayList<>();

    private Reservation reservation;
    private String createReservationPath;

    @Before
    public void setUp() {
        reservation = new Reservation(RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                PASSENGERS);

        UriBuilder uriBuilderRequest = UriBuilder.fromUri(EventsResource.PATH);
        createReservationPath = uriBuilderRequest
                .path(EventsResource.RESERVATION_CREATED)
                .toString();
    }

    @Test
    public void givenValidReservation_whenCreatingReservation_shouldCreateReservation() {
        UriBuilder uriBuilder = UriBuilder.fromUri(ReservationsResource.PATH);
        String reservationNumberString = Integer.toString(RESERVATION_NUMBER);
        String locationExpected = uriBuilder.path(reservationNumberString).toString();

        givenBaseRequest().body(reservation)
                .when()
                .post(createReservationPath)
                .then()
                .statusCode(CREATED.getStatusCode())
                .header("Location", buildUrl(locationExpected));
    }
}