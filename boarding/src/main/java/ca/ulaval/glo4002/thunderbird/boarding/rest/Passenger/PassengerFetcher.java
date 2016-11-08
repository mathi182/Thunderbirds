package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

public class PassengerFetcher {
    public static final String URL = "http://127.0.0.1:8888";
    public static final String SERVICE_PATH_FORMAT = "/passengers/%1s";

    public Passenger fetchPassenger(UUID validPassengerHash) {
        String url = URL + String.format(SERVICE_PATH_FORMAT, validPassengerHash.toString());
        getResource(url);

        return null;
    }

    private static ClientResponse getResource(String url) {
        return Client
                .create()
                .resource(url)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
    }
}
