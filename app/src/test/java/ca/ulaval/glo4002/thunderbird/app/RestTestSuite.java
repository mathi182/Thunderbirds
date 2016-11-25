package ca.ulaval.glo4002.thunderbird.app;


import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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
    }

    @AfterClass
    public static void afterClass() {
        boardingThread.interrupt();
        reservationThread.interrupt();
    }
}