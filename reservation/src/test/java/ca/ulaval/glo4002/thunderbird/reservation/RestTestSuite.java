package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import ca.ulaval.glo4002.thunderbird.reservation.event.EventsResourceRestTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({EventsResourceRestTest.class})
public class RestTestSuite {

    public static final int TEST_SERVER_PORT = 9292;

    private static ReservationServer reservationServer;

    @BeforeClass
    public static void setUpClass() {
        reservationServer = new ReservationServer();
        reservationServer.start(TEST_SERVER_PORT, new DevContext());
    }

    @AfterClass
    public static void tearDownClass() {
        reservationServer.stop();
    }

}
