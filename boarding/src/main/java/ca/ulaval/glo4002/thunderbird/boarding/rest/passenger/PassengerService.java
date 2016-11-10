package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static com.sun.jersey.api.client.ClientResponse.Status.PARTIAL_CONTENT;

public class PassengerService {

    private PassengerAssembler passengerAssembler;
    private static final String SERVICE_LOCATION = "http://127.0.0.1:8888";
    private static final String SERVICE_PATH_FORMAT = "/passengers/%1s";


    public PassengerService(PassengerAssembler passengerAssembler){
        this.passengerAssembler = passengerAssembler;
    }

    public Passenger fetchPassenger(UUID passengerHash) {
        String url = SERVICE_LOCATION + String.format(SERVICE_PATH_FORMAT,passengerHash);

        try {
            ClientResponse response = getResource(url);
            validateResponse(response, passengerHash);
            PassengerDTO request = response.getEntity(PassengerDTO.class);
            return getPassengerFromRequest(request);
        } catch (Exception e) {
            throw new PassengerNotFoundException(passengerHash);
        }


    }

    private ClientResponse getResource(String url) {
        return Client
                .create()
                .resource(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
    }

    private void validateResponse(ClientResponse response, UUID passengerHash) {
        if (response.getStatus() != OK.getStatusCode())
            throw new PassengerNotFoundException(passengerHash);
    }

    private Passenger getPassengerFromRequest(PassengerDTO request) {
        return passengerAssembler.toDomain(request);
    }
}
