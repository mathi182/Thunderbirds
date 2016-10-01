package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class ReservationServer implements Runnable {

    public static final int DEFAULT_PORT = 8787;

    public static void main(String[] args) {
        new ReservationServer().run();
    }

    public void run() {
        int httpPort = getHttpPort();

        Server server = new Server(httpPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        configurerJersey(servletContextHandler);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }

    private void configurerJersey(ServletContextHandler servletContextHandler) {
        ServletContainer container = new ServletContainer(new ResourceConfig().packages("ca.ulaval.glo4002.thunderbird.reservation"));
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
    }

    private int getHttpPort() {
        try {
            return Integer.parseInt(System.getProperty("reservation.port"));
        } catch (NumberFormatException e) {
            return DEFAULT_PORT;
        }
    }
}