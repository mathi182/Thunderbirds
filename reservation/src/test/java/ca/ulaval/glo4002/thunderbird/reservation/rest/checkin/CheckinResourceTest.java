package ca.ulaval.glo4002.thunderbird.reservation.rest.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.rest.passenger.Passenger;
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

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Passenger.class)
public class CheckinResourceTest {
    public static final String CHECKIN_ID = "checkinId";
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @InjectMocks
    CheckinResource checkinResource;
    private Checkin checkinMock;

    @Before
    public void setUp() throws Exception {
        mockStatic(Passenger.class);
        checkinMock = mock(Checkin.class);
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_locationShouldBeAdequate() {
        willReturn(CHECKIN_ID).given(checkinMock).getCheckinId();

        Response responseActual = checkinResource.checkin(checkinMock);
        String actualLocation = responseActual.getLocation().toString();

        String expectedLocation = "/checkins/" + CHECKIN_ID;
        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldCompletePassengerCheckin() {
        checkinResource.checkin(checkinMock);

        verify(checkinMock, times(1)).completePassengerCheckin();
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldReturnCreatedStatusCode() {
        Response responseActual = checkinResource.checkin(checkinMock);
        int actualStatus = responseActual.getStatus();

        int expectedStatus = CREATED.getStatusCode();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test(expected = MissingCheckinFieldException.class)
    public void givenInvalidCheckin_whenCheckin_shouldReturnBadRequest() {
        willThrow(MissingCheckinFieldException.class).given(checkinMock).completePassengerCheckin();

        Response responseActual = checkinResource.checkin(checkinMock);
    }
}
