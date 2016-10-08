package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.Checkin;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinAssembler;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinResource;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerAssembly;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PassengerAssembly.class)
public class CheckinResourceTest {
    private static final String AGENT_ID = "agentId";
    private static final String PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO =
            "passenger_hash_with_reservation_and_missing_info";
    private static final String CHECKIN_ID = "checkinId";

    @Mock
    public PassengerAssembly invalidReservationPassenger = new PassengerAssembly(12345, "", "", 21, "", "seatClass");

    @Mock
    public PassengerAssembly validReservationPassenger = new PassengerAssembly(12345, "alex", "brillant", 21,
            "passportNumbe", "seatClass");

    @Mock
    public PassengerAssembly invalidSelfCheckinPassenger = new PassengerAssembly(12345, "alex", "brillant", 21,
            "passportNumbe", "seatClass");

    private CheckinAssembler checkinAssemblerMock;
    private Checkin checkinMock;

    @Mock
    Checkin checkinSelfInvalidDate;

    @InjectMocks
    CheckinResource checkinResource;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        mockStatic(PassengerAssembly.class);
        checkinAssemblerMock = mock(CheckinAssembler.class);
        checkinMock = mock(Checkin.class);

        willReturn(checkinMock).given(checkinAssemblerMock).toDomain();
    }

    @Test
    public void givenCheckinNull_whenCheckin_shouldNotCreateCheckIn() {
        CheckinAssembler checkinAssemblerNull = new CheckinAssembler();
        checkinAssemblerNull.passengerHash = null;
        checkinAssemblerNull.passengerHash = null;

        Response responseActual = checkinResource.checkin(checkinAssemblerNull);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldCreateCheckIn() {
        willReturn(true).given(checkinMock).isValid();
        willReturn(true).given(checkinMock).passengerExist();
        willReturn(CHECKIN_ID).given(checkinMock).getCheckinId();
        Response responseActual = checkinResource.checkin(checkinAssemblerMock);

        int statusActual = responseActual.getStatus();
        String actualLocation = responseActual.getLocation().toString();

        int statusExpected = CREATED.getStatusCode();
        assertEquals(statusExpected, statusActual);
        String expectedLocation = "/checkins/" + checkinMock.getCheckinId();
        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void givenCheckinPassengerWithInvalidInfo_whenCheckin_shouldReturnBadRequest() {
        CheckinAssembler checkinPassengerWithReservationButMissingInfo = mock(CheckinAssembler.class);
        Checkin checkin = mock(Checkin.class);
        when(checkin.passengerExist()).thenReturn(true);
        when(checkinPassengerWithReservationButMissingInfo.toDomain()).thenReturn(checkin);
        when(invalidReservationPassenger.isValidForCheckin()).thenReturn(false);
        checkinPassengerWithReservationButMissingInfo.passengerHash = PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO;
        checkinPassengerWithReservationButMissingInfo.agentId = AGENT_ID;
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO)).willReturn
                (invalidReservationPassenger);

        Response responseActual = checkinResource.checkin(checkinPassengerWithReservationButMissingInfo);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenCheckinPassengerWithInvalidPassenger_whenCheckin_shouldReturnNotFound() {
        CheckinAssembler checkinPassengerWithReservationButMissingInfo = mock(CheckinAssembler.class);
        Checkin checkin = mock(Checkin.class);
        when(checkinPassengerWithReservationButMissingInfo.toDomain()).thenReturn(checkin);
        when(checkin.isValid()).thenReturn(true);
        when(checkin.passengerExist()).thenReturn(false);
        when(invalidReservationPassenger.isValidForCheckin()).thenReturn(false);
        checkinPassengerWithReservationButMissingInfo.passengerHash = PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO;
        checkinPassengerWithReservationButMissingInfo.agentId = AGENT_ID;
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO)).willReturn
                (invalidReservationPassenger);

        Response responseActual = checkinResource.checkin(checkinPassengerWithReservationButMissingInfo);
        int statusActual = responseActual.getStatus();

        int statusExpected = NOT_FOUND.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }
}