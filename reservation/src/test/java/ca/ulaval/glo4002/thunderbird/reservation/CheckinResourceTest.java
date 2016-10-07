package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.Checkin;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinAssembler;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinResource;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Passenger.class)
public class CheckinResourceTest {
    public static final String AGENT_ID = "agentId";
    public static final String PASSENGER_HASH_WITH_RESERVATION = "passenger_hash_with_reservation";
    public static final String PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO = "passenger_hash_with_reservation_and_missing_info";

    @Mock
    public Passenger invalidReservationPassenger = new Passenger(12345, "", "", 21, "", "seatClass");

    @Mock
    public Passenger validReservationPassenger = new Passenger(12345, "alex", "brillant", 21, "passportNumbe", "seatClass");

    @Mock
    public Passenger invalidSelfCheckinPassenger = new Passenger(12345, "alex", "brillant", 21, "passportNumbe", "seatClass");

    private CheckinAssembler checkinValid;
    private CheckinAssembler checkinNull;

    @Mock Checkin checkinSelfInvalidDate;

    @InjectMocks
    CheckinResource checkinResource;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Passenger.class);
    }

    @Test
    public void givenCheckinNull_whenCheckin_shouldNotCreateCheckIn() throws Exception {
        this.checkinNull = new CheckinAssembler();
        this.checkinNull.passengerHash = null;
        this.checkinNull.passengerHash = null;

        Response responseActual = this.checkinResource.checkin(this.checkinNull);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldCreateCheckIn() throws Exception {
        this.checkinValid = new CheckinAssembler();
        when(validReservationPassenger.isValidForCheckin()).thenReturn(true);
        BDDMockito.given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(validReservationPassenger);
        this.checkinValid.passengerHash = PASSENGER_HASH_WITH_RESERVATION;
        this.checkinValid.agentId = AGENT_ID;

        Checkin checkin = this.checkinValid.toDomain();
        when(this.checkinValid.toDomain()).thenReturn(checkin);

        Response responseActual = this.checkinResource.checkin(this.checkinValid);
        String locationActual = responseActual.getLocation().toString();
        int statusActual = responseActual.getStatus();

        String checkinId = checkin.getCheckinId();
        String locationExpected = "/checkins/"+checkinId;
        int statusExpected = CREATED.getStatusCode();
        assertEquals(locationExpected, locationActual);
        assertEquals(statusExpected, statusActual);
        assertEquals(Checkin.findByCheckinId(checkinId), this.checkinValid);
    }

    @Test
    public void givenCheckinPassengerWithInvalidInfo_whenCheckin_shouldNotCreateCheckin() throws Exception {
        CheckinAssembler checkinPassengerWithReservationButMissingInfo = mock(CheckinAssembler.class);
        Checkin checkin = mock(Checkin.class);
        when(checkinPassengerWithReservationButMissingInfo.toDomain()).thenReturn(checkin);

        when(invalidReservationPassenger.isValidForCheckin()).thenReturn(false);
        this.checkinPassengerWithReservationButMissingInfo.passengerHash = PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO;
        this.checkinPassengerWithReservationButMissingInfo.agentId = AGENT_ID;
        BDDMockito.given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO)).willReturn(invalidReservationPassenger);

        Response responseActual = this.checkinResource.checkin(this.checkinPassengerWithReservationButMissingInfo);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }
}