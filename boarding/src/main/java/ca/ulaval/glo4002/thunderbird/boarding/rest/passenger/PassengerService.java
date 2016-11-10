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
    private PassengerRequest passengerRequest;
    private static final String SERVICE_LOCATION = "http://127.0.0.1:8787";
    private static final String SERVICE_PATH_FORMAT = "/passengers/%1s";


    public PassengerService(PassengerAssembler passengerAssembler, PassengerRequest passengerRequest){
        this.passengerAssembler = passengerAssembler;
        this.passengerRequest = passengerRequest;
    }

    public Passenger fetchPassenger(UUID passengerHash) {
        ClientResponse response = passengerRequest.getPassengerResponse(passengerHash.toString());
        validateResponse(response, passengerHash);
        PassengerDTO request = response.getEntity(PassengerDTO.class);
        return getPassengerFromRequest(request);
    }

    private void validateResponse(ClientResponse response, UUID passengerHash) {
        if (response.getStatus() != OK.getStatusCode())
            throw new PassengerNotFoundException(passengerHash);
    }

    private Passenger getPassengerFromRequest(PassengerDTO request) {
        return passengerAssembler.toDomain(request);
    }
}
