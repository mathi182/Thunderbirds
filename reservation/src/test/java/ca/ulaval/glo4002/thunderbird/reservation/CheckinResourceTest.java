package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.Checkin;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinAssembler;
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
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(PassengerStorage.class)
public class CheckinResourceTest {
    public static final String AGENT_ID = "agentId";
    public static final String PASSENGER_HASH_WITH_RESERVATION = "passenger_hash_with_reservation";
    public static final String PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO = "passenger_hash_with_reservation_and_missing_info";
    public static final String CHECKIN_ID = "checkinId";

    @Mock
    public PassengerStorage invalidReservationPassenger = new PassengerStorage(12345, "", "", 21, "", "seatClass");

    @Mock
    public PassengerStorage validReservationPassenger = new PassengerStorage(12345, "alex", "brillant", 21, "passportNumbe", "seatClass");

    @Mock
    public PassengerStorage invalidSelfCheckinPassenger = new PassengerStorage(12345, "alex", "brillant", 21, "passportNumbe", "seatClass");

    private CheckinAssembler checkinAssemblerMock;
    private CheckinAssembler checkinAssemblerNull;
    private Checkin checkinMock;

    @Mock Checkin checkinSelfInvalidDate;

    @InjectMocks
    CheckinResource checkinResource;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(PassengerStorage.class);
        checkinAssemblerMock = mock(CheckinAssembler.class);
        checkinMock = mock(Checkin.class);

        willReturn(checkinMock).given(checkinAssemblerMock).toDomain();
    }

    @Test
    public void givenCheckinNull_whenCheckin_shouldNotCreateCheckIn() throws Exception {
        this.checkinAssemblerNull = new CheckinAssembler();
        this.checkinAssemblerNull.passengerHash = null;
        this.checkinAssemblerNull.passengerHash = null;

        Response responseActual = this.checkinResource.checkin(this.checkinAssemblerNull);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_ShouldCreateCheckIn() throws Exception{
        willReturn(true).given(checkinMock).isValid();
        willReturn(true).given(checkinMock).passengerExist();
        willReturn(true).given(checkinMock).isComplete();
        willReturn(CHECKIN_ID).given(checkinMock).getCheckinId();
        Response responseActual = this.checkinResource.checkin(checkinAssemblerMock);

        int statusExpected = CREATED.getStatusCode();
        int statusActual = responseActual.getStatus();
        String actualLocation = responseActual.getLocation().toString();
        String expectedLocation = "/checkins/" + checkinMock.getCheckinId();
        assertEquals(statusExpected , statusActual);
        assertEquals(expectedLocation, actualLocation);
    }


    @Test
    public void givenCheckinPassengerWithInvalidInfo_whenCheckin_shouldNotCreateCheckin() throws Exception {
        CheckinAssembler checkinPassengerWithReservationButMissingInfo = mock(CheckinAssembler.class);
        Checkin checkin = mock(Checkin.class);
        when(checkinPassengerWithReservationButMissingInfo.toDomain()).thenReturn(checkin);

        when(invalidReservationPassenger.isValidForCheckin()).thenReturn(false);
        checkinPassengerWithReservationButMissingInfo.passengerHash = PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO;
        checkinPassengerWithReservationButMissingInfo.agentId = AGENT_ID;
        BDDMockito.given(PassengerStorage.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION_AND_MISSING_INFO)).willReturn(invalidReservationPassenger);

        Response responseActual = this.checkinResource.checkin(checkinPassengerWithReservationButMissingInfo);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }
}