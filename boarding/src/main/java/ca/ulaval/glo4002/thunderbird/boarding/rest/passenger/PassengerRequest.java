package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;

import static java.util.Optional.ofNullable;

public class PassengerRequest {
    public static final String DOMAIN_NAME = "localhost";
    public static final String PATH = "passengers";

    private static final String PORT_PROPERTY = "reservation.port";
    private static final int DEFAULT_PORT = 8787;

    public ClientResponse getPassengerResponse(String passengerHash) {
        int httpPort = ofNullable(System.getProperty(PORT_PROPERTY)).map(Integer::parseInt).orElse(DEFAULT_PORT);
        String url = String.format("%s:%d/%s/%s", DOMAIN_NAME, httpPort, PATH, passengerHash);

        return getResource(url);
    }

    public ClientResponse getResource(String url) {
        return Client
                .create()
                .resource(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
    }
}