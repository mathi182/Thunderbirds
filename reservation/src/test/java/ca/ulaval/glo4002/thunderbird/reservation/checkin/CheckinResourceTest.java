package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CheckinResourceTest {
    public static final String CHECKIN_ID = "checkinId";

    private CheckinResource checkinResource;
    private Checkin checkinMock;

    @Before
    public void setUp() {
        checkinResource = new CheckinResource();

        checkinMock = mock(Checkin.class);
        willReturn(CHECKIN_ID).given(checkinMock).getCheckinId();
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldCompletePassengerCheckin() {
        checkinResource.checkin(checkinMock);

        verify(checkinMock).completePassengerCheckin(any());
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_locationShouldBeAdequate() {
        Response responseActual = checkinResource.checkin(checkinMock);

        String actualLocation = responseActual.getLocation().toString();
        String expectedLocation = "/checkins/" + CHECKIN_ID;
        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldReturnCreatedStatusCode() {
        Response responseActual = checkinResource.checkin(checkinMock);

        int actualStatus = responseActual.getStatus();
        int expectedStatus = CREATED.getStatusCode();
        assertEquals(expectedStatus, actualStatus);
    }
}
