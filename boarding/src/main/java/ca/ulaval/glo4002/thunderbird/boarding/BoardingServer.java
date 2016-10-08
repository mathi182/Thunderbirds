package ca.ulaval.glo4002.thunderbird.boarding;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class BoardingServer implements Runnable {

    private static final int DEFAULT_PORT = 8888;

    public static void main(String[] args) {
        new BoardingServer().run();
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
        ServletContainer container = new ServletContainer(new ResourceConfig().packages("ca.ulaval.glo4002.thunderbird.boarding"));
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
    }

    private int getHttpPort() {
        try {
            return Integer.parseInt(System.getProperty("boarding.port"));
        } catch (NumberFormatException e) {
            return DEFAULT_PORT;
        }
    }
}