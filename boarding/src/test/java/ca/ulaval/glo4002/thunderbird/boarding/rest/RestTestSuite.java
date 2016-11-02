package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
})
public class RestTestSuite {

    public static final int TEST_SERVER_PORT = 8787;

    private static BoardingServer boardingServer;

    @BeforeClass
    public static void setUpClass() {
        boardingServer = new BoardingServer();
        DevContext devContext = new DevContext();
        boardingServer.start(TEST_SERVER_PORT, devContext);
    }

    @AfterClass
    public static void tearDownClass() {
        boardingServer.stop();
    }

}
