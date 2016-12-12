package runners;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import cucumber.api.java.Before;

public class JettyStarterHook {

    private static boolean isFirstFeature = true;

    private BoardingServer boardingServer;
    private ReservationServer reservationServer;

    @Before
    public void beforeAll() throws Exception {
        if (isFirstFeature) {
            Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
            startJettyServer();
            isFirstFeature = false;
        }
    }

    private void startJettyServer() throws Exception {
        boardingServer = new BoardingServer();
        reservationServer = new ReservationServer();
        Thread boardingThread = new Thread(boardingServer);
        Thread reservationThread = new Thread(reservationServer);

        boardingThread.start();
        reservationThread.start();

        boardingThread.join();
        reservationThread.join();
    }

    private class JettyServerShutdown extends Thread {
        public void run() {
            try {
                boardingServer.stop();
                reservationServer.stop();
            } catch (Exception e) {
                // Nothing do to anyways
            }
        }
    }
}
