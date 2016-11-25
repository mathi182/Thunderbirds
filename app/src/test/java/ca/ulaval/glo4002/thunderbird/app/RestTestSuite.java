package ca.ulaval.glo4002.thunderbird.app;


import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AppRestTest.class
})
public class RestTestSuite {
    @BeforeClass
    public static void setUpClass() throws InterruptedException{
        Thread boardingThread = new Thread(new BoardingServer());
        Thread reservationThread = new Thread(new ReservationServer());

        boardingThread.start();
        reservationThread.start();
    }
}