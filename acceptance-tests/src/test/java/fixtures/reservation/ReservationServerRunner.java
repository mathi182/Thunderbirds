package fixtures.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import contexts.reservation.ReservationContext;

public class ReservationServerRunner {
    public static final int JETTY_TEST_PORT = 15146;

    private static boolean isFirstFeature = true;
    private ReservationServer server;

    public void startJettyServer() {
        if (isFirstFeature) {
            isFirstFeature = false;
            Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
            startMedServerInJetty();
        }
    }

    private void startMedServerInJetty() {
        server = new ReservationServer();
        server.start(JETTY_TEST_PORT, new ReservationContext());
    }

    private class JettyServerShutdown extends Thread {
        public void run() {
            try {
                server.stop();
            } catch (Exception e) {
                // Nothing do to anyways
            }
        }
    }
}