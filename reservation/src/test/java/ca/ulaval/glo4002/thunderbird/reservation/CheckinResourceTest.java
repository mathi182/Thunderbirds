package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.Checkin;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinResource;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerStorage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(PassengerStorage.class)
public class CheckinResourceTest {
    public static final String AGENT_ID = "agentId";
    public static final String CHECKIN_CREATED_URI = "/checkins/checkinId";

    public static final String PASSENGER_HASH_WITHOUT_RESERVATION = "passenger_hash_without_reservation";
    public static final String PASSENGER_HASH_WITH_RESERVATION = "passenger_hash_with_reservation";

    public static final String PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO = "passenger_hash_with_reservation_and_missing_info";

    @Mock
    public static final PassengerStorage INVALID_RESERVATION_PASSENGER = new PassengerStorage(12345, "", "", 21, "", "seatClass");

    @Mock
    public static final PassengerStorage VALID_RESERVATION_PASSENGER = new PassengerStorage(12345, "alex", "brillant", 21, "passportNumbe", "seatClass");

    private Checkin checkinValid;
    private Checkin checkinPassengerWithoutReservation;
    private Checkin checkinNull;
    private Checkin checkinPassengerWithReservationButMissingInfo;

    @InjectMocks
    CheckinResource checkinResource;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(PassengerStorage.class);
        this.checkinValid = new Checkin(PASSENGER_HASH_WITH_RESERVATION, AGENT_ID);
        this.checkinNull = new Checkin(null, null);
        this.checkinPassengerWithoutReservation = new Checkin(PASSENGER_HASH_WITHOUT_RESERVATION, AGENT_ID);
        this.checkinPassengerWithReservationButMissingInfo = new Checkin(PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO, AGENT_ID);

        when(VALID_RESERVATION_PASSENGER.isValidForCheckin()).thenReturn(true);
        when(INVALID_RESERVATION_PASSENGER.isValidForCheckin()).thenReturn(false);

        BDDMockito.given(PassengerStorage.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(VALID_RESERVATION_PASSENGER);
        BDDMockito.given(PassengerStorage.findByPassengerHash(PASSENGER_HASH_WITHOUT_RESERVATION)).willReturn(null);
        BDDMockito.given(PassengerStorage.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO)).willReturn(INVALID_RESERVATION_PASSENGER);
    }

    @Test
    public void givenCheckinNull_whenCheckin_shouldNotCreateCheckIn() throws Exception {
        Response responseActual = this.checkinResource.checkin(this.checkinNull);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenCheckinPassengerWithReservation_whenCheckin_shouldCreateCheckIn() throws Exception {
        Response responseActual = this.checkinResource.checkin(this.checkinValid);
        String locationActual = responseActual.getLocation().toString();
        int statusActual = responseActual.getStatus();

        String locationExpected = CHECKIN_CREATED_URI;
        int statusExpected = CREATED.getStatusCode();
        assertEquals(locationExpected, locationActual);
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenCheckinPassengerWithoutReservation_whenCheckin_shouldNotCreateCheckin() throws Exception {
        Response responseActual = this.checkinResource.checkin(this.checkinPassengerWithoutReservation);
        int statusActual = responseActual.getStatus();

        int statusExpected = NOT_FOUND.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenCheckinPassengerWithInvalidInfo_whenCheckin_shouldNotCreateCheckin() throws Exception {
        Response responseActual = this.checkinResource.checkin(this.checkinPassengerWithReservationButMissingInfo);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }
}