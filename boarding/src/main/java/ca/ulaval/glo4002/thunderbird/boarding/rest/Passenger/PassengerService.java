package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger.PassengerAPICaller;
import ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger.PassengerAssembler;
import ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger.PassengerDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;

public class PassengerService {

    private PassengerAssembler passengerAssembler;
    public static final String SERVICE_LOCATION = "http://127.0.0.1:8888";
    public static final String SERVICE_PATH_FORMAT = "/passengers/%1s";


    public PassengerService(PassengerAssembler passengerAssembler){
        this.passengerAssembler = passengerAssembler;
    }

    public Passenger fetchPassenger(UUID PassengerHash) {
        PassengerDTO request = getPassengerRequestFromAPI(PassengerHash);
        return getPassengerFromRequest(request);
    }

    private PassengerDTO getPassengerRequestFromAPI(UUID passengerHash) {
        ClientResponse response = requestPassenger(passengerHash.toString());
        validateResponse(response, passengerHash);
        return response.getEntity(PassengerDTO.class);
    }

    private void validateResponse(ClientResponse response, UUID passengerHash) {
        if (response.getStatus() != OK.getStatusCode())
            throw new PassengerNotFoundException(passengerHash);
    }

    private Passenger getPassengerFromRequest(PassengerDTO request) {
        return passengerAssembler.toDomain(request);
    }

    private ClientResponse requestPassenger(String passengerHash) {
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
