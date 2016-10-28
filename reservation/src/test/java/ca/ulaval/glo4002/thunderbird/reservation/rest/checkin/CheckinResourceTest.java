package ca.ulaval.glo4002.thunderbird.reservation.rest.checkin;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CheckinResourceTest {
    public static final String CHECKIN_ID = "checkinId";

    private CheckinResource checkinResource;
    private Checkin checkin;

    @Before
    public void setUp() {
        checkinResource = new CheckinResource();

        checkin = mock(Checkin.class);
        willReturn(CHECKIN_ID).given(checkin).getCheckinId();
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldCompletePassengerCheckin() {
        checkinResource.checkin(checkin);

        verify(checkin).completePassengerCheckin();
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_locationShouldBeAdequate() {
        Response responseActual = checkinResource.checkin(checkin);

        String actualLocation = responseActual.getLocation().toString();
        String expectedLocation = "/checkins/" + CHECKIN_ID;
        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void givenCheckinPassengerValid_whenCheckin_shouldReturnCreatedStatusCode() {
        Response responseActual = checkinResource.checkin(checkin);

        int actualStatus = responseActual.getStatus();
        int expectedStatus = CREATED.getStatusCode();
        assertEquals(expectedStatus, actualStatus);
    }
}