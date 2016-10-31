package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationsResourceRestTest;
import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SeatAssignationsResourceRestTest.class
})
public class RestTestSuite {

    public static final int TEST_SERVER_PORT = 8787;

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
