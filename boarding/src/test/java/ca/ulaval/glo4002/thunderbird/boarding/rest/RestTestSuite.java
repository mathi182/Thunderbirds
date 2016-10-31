package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationsResourceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SeatAssignationsResourceTest.class})
public class RestTestSuite {

    public static final int TEST_SERVER_PORT = 9393;

    private static BoardingServer boardingServer;

    @BeforeClass
    public static void setUpClass() {
        boardingServer = new BoardingServer();
        boardingServer.start(TEST_SERVER_PORT, new DevContext());
    }

    @AfterClass
    public static void tearDownClass() {
        boardingServer.stop();
    }

}
