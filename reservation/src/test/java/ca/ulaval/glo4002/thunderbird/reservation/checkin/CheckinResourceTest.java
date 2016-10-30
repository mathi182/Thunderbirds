package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
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
    public void whenCheckin_shouldCompletePassengerCheckin() {
        checkinResource.checkin(checkin);

        verify(checkin).completePassengerCheckin(any());
        verify(checkin).save();
    }

    @Test
    public void whenCheckin_locationShouldBeAdequate() {
        Response responseActual = checkinResource.checkin(checkin);

        String actualLocation = responseActual.getLocation().toString();
        String expectedLocation = "/checkins/" + CHECKIN_ID;
        assertEquals(expectedLocation, actualLocation);
    }

}