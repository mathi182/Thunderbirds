package fixtures.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import contexts.boarding.BoardingContext;

public class BoardingServerRunner {
    public static final int JETTY_TEST_PORT = 15141;

    private static boolean isFirstFeature = true;
    private BoardingServer server;

    public void startJettyServer() {
        if (isFirstFeature) {
            isFirstFeature = false;
            Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
            startMedServerInJetty();
        }
    }

    private void startMedServerInJetty() {
        server = new BoardingServer();
        server.start(JETTY_TEST_PORT, new BoardingContext());
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