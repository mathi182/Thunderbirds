package ca.ulaval.glo4002.thunderbird.reservation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.net.URI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by alexandre on 2016-09-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CheckinResourceTest {
    public static final String PASSENGER_HASH_WITH_RESERVATION = "passenger_hash_with_reservation";
    public static final String AGENT_ID = "agentId";
    public static final String CHECKIN_CREATED_URI = "/checkins/checkinId";
    public static final String PASSENGER_HASH_WITHOUT_RESERVATION = "passenger_hash_without_reservation";
    private Checkin checkinValid;
    private Checkin checkinPassengerWithoutReservation;
    private Checkin checkinNull;

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    CheckinResource checkinResource;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        this.checkinValid = new Checkin(PASSENGER_HASH_WITH_RESERVATION, AGENT_ID);
        this.checkinNull = new Checkin();
        this.checkinPassengerWithoutReservation = new Checkin(PASSENGER_HASH_WITHOUT_RESERVATION, AGENT_ID);
        when(this.reservationRepository.passengerHasReservation(PASSENGER_HASH_WITH_RESERVATION)).thenReturn(true);
        when(this.reservationRepository.passengerHasReservation(PASSENGER_HASH_WITHOUT_RESERVATION)).thenReturn(false);
    }

    @Test
    public void checkinNull_checkin_notCreateCheckIn() throws Exception {
        Response responseActual = this.checkinResource.checkin(this.checkinNull);
        int statusActual = responseActual.getStatus();

        int statusExpected = 400;
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void checkinPassengerWithReservation_checkin_createCheckIn() throws Exception {
        Response responseActual = this.checkinResource.checkin(this.checkinValid);
        String locationActual = responseActual.getLocation().toString();
        int statusActual = responseActual.getStatus();

        Response responseExpected = Response.created(URI.create(CHECKIN_CREATED_URI)).build();
        String locationExpected = responseExpected.getLocation().toString();
        int statusExpected = responseExpected.getStatus();
        assertEquals(locationExpected, locationActual);
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void checkinPassengerWithoutReservation_checkin_notCreateCheckin() throws Exception {
        Response responseActual = this.checkinResource.checkin(this.checkinPassengerWithoutReservation);
        int statusActual = responseActual.getStatus();

        int statusExpected = 404;
        assertEquals(statusExpected, statusActual);
    }
}