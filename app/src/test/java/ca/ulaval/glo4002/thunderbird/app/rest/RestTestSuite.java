package ca.ulaval.glo4002.thunderbird.app.rest;


import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.concurrent.TimeUnit;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AppRestTest.class
})
public class RestTestSuite {
    private static Thread boardingThread;
    private static Thread reservationThread;

    @BeforeClass
    public static void setUpClass() throws InterruptedException{
        boardingThread = new Thread(new BoardingServer());
        reservationThread = new Thread(new ReservationServer());

        boardingThread.start();
        reservationThread.start();

        TimeUnit.SECONDS.sleep(12); //Waiting for the servers to be fully started
    }

    @AfterClass
    public static void afterClass() {
        boardingThread.interrupt();
        reservationThread.interrupt();
    }
}