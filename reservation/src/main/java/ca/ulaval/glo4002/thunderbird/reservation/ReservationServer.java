package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.contexts.Context;
import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import ca.ulaval.glo4002.thunderbird.reservation.contexts.ProdContext;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerContextFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

import static java.util.Optional.ofNullable;

public class ReservationServer {

    private static final String PORT_PROPERTY = "reservation.port";
    private static final int DEFAULT_PORT = 8787;

    private static final String CONTEXT_PROPERTY = "context";
    private static final String DEFAULT_CONTEXT = "prod";
    private Server server;

    public static void main(String[] args) {
        int httpPort = ofNullable(System.getProperty(PORT_PROPERTY)).map(Integer::parseInt).orElse(DEFAULT_PORT);
        Context context = resolveContext(ofNullable(System.getProperty(CONTEXT_PROPERTY)).orElse(DEFAULT_CONTEXT));

        ReservationServer server = new ReservationServer();
        server.start(httpPort, context);
        server.join();
    }

    private static Context resolveContext(String contextName) {
        switch (contextName) {
            case "prod":
                return new ProdContext();
            case "dev":
                return new DevContext();
            default:
                throw new RuntimeException("Cannot load context " + contextName);
        }
    }

    public void start(int httpPort, Context context) {
        context.apply();

        server = new Server(httpPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        configurerJersey(servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void join() {
        try {
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tryStopServer();
        }
    }

    private void tryStopServer() {
        try {
            server.destroy();
        } catch (Exception e) {
            return;
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurerJersey(ServletContextHandler servletContextHandler) {
        ResourceConfig resourceConfig = new ResourceConfig().packages("ca.ulaval.glo4002.thunderbird.reservation");
        ServletContainer container = new ServletContainer(resourceConfig);
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
        servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    }

}