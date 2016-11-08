package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

public class PassengerFetcher {
    public static final String URL = "http://127.0.0.1:8888";
    public static final String SERVICE_PATH_FORMAT = "/passenger/%1s";

    public Passenger fetchPassenger(UUID PassengerHash) {
        PassengerRequest request = getPassengerRequestFromAPI(PassengerHash.toString());
        Passenger passenger = getPassengerFromRequest(request);
        return passenger;
    }

    private PassengerRequest getPassengerRequestFromAPI(String passengerHash){
        String url = URL + String.format(SERVICE_PATH_FORMAT, passengerHash);
        ClientResponse response = getResource(url);
        return response.getEntity(PassengerRequest.class);
    }
    private ClientResponse getResource(String url) {
        return Client
                .create()
                .resource(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
    }

    private Passenger getPassengerFromRequest(PassengerRequest request){
        return new PassengerAssembler().toDomain(request);
    }
}
