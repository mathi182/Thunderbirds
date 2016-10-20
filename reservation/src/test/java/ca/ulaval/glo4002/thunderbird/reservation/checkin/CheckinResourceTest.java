package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Passenger.class)
public class CheckinResourceTest {
    public static final String CHECKIN_ID = "checkinId";
    private Checkin checkinMock;

    @InjectMocks
    CheckinResource checkinResource;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        mockStatic(Passenger.class);
        checkinMock = mock(Checkin.class);
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldCreateCheckIn() {
        willReturn(true).given(checkinMock).isValid();
        willReturn(true).given(checkinMock).passengerExist();
        willReturn(CHECKIN_ID).given(checkinMock).getCheckinId();
        Response responseActual = checkinResource.checkin(checkinMock);
        int statusActual = responseActual.getStatus();
        String actualLocation = responseActual.getLocation().toString();

        int statusExpected = CREATED.getStatusCode();
        assertEquals(statusExpected, statusActual);

        String expectedLocation = "/checkins/" + checkinMock.getCheckinId();
        verify(checkinMock, times(1)).completePassengerCheckin();
        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void givenInvalidCheckin_whenCheckin_shouldReturnBadRequest() {
        willReturn(true).given(checkinMock).isValid();
        willReturn(true).given(checkinMock).passengerExist();
        willReturn(false).given(checkinMock).isValid();

        Response responseActual = checkinResource.checkin(checkinMock);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenCheckinPassengerWithInvalidPassenger_whenCheckin_shouldReturnNotFound() {
        willReturn(true).given(checkinMock).isValid();
        willReturn(false).given(checkinMock).passengerExist();
        willReturn(false).given(checkinMock).passengerExist();

        Response responseActual = checkinResource.checkin(checkinMock);
        int statusActual = responseActual.getStatus();

        int statusExpected = NOT_FOUND.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }
}