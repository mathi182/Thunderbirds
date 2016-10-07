package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AlexisLessard on 2016-10-06.
 */
public class CheckinTest {
    private static final String PASSENGER_HASH = "passenger_hash";
    private static final String SELF_CHECKIN_AGENT_ID = "SELF";
    private static final String AGENT_ID = "not_self";

    @Test
    public void whenCreatingNewSelfCheckin_ShouldBeSelfCheckin() throws Exception{
        Checkin checkintest = new Checkin(PASSENGER_HASH,SELF_CHECKIN_AGENT_ID);

        assertTrue(checkintest.isSelfCheckin());
    }

    @Test
    public void whenCreatingNewCheckin_ShouldNotBeSelfCheckin() throws Exception{
        Checkin checkintest = new Checkin(PASSENGER_HASH,AGENT_ID);

        assertFalse(checkintest.isSelfCheckin());
    }

}