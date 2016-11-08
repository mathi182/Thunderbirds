package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;

public class PassengerFetcher {
    public static final String URL = "http://127.0.0.1:8888";
    public static final String SERVICE_PATH_FORMAT = "/passenger/%1s";

    public Passenger fetchPassenger(UUID PassengerHash) {
        PassengerRequest request = getPassengerRequestFromAPI(PassengerHash);
        Passenger passenger = getPassengerFromRequest(request);
        return passenger;
    }

    private PassengerRequest getPassengerRequestFromAPI(UUID passengerHash){
        String url = URL + String.format(SERVICE_PATH_FORMAT, passengerHash);;
        ClientResponse response = getResource(url);
        validateResponse(response,passengerHash);
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

    private void validateResponse(ClientResponse response, UUID passengerHash){
        if(response.getStatus() != OK.getStatusCode())
            throw new PassengerNotFoundException(passengerHash);
    }
    private Passenger getPassengerFromRequest(PassengerRequest request){
        return new PassengerAssembler().toDomain(request);
    }

}
