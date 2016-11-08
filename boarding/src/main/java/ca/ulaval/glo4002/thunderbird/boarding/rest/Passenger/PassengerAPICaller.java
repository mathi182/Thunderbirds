package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

public class PassengerAPICaller {
    public static final String SERVICE_LOCATION = "http://127.0.0.1:8888";
    public static final String SERVICE_PATH_FORMAT = "/passenger/%1s";

    public ClientResponse requestPassenger(String passengerHash) {
        String url = SERVICE_LOCATION + String.format(SERVICE_PATH_FORMAT,passengerHash);
        return getResource(url);
    }

    private ClientResponse getResource(String url) {
        return Client
                .create()
                .resource(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
    }
}
