package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;

public class PassengerFetcher {

    private PassengerAssembler passengerAssembler;
    private PassengerAPICaller apiCaller;

    public PassengerFetcher(PassengerAssembler passengerAssembler, PassengerAPICaller apiCaller){
        this.passengerAssembler = passengerAssembler;
        this.apiCaller = apiCaller;
    }

    public Passenger fetchPassenger(UUID PassengerHash) {
        PassengerRequest request = getPassengerRequestFromAPI(PassengerHash);
        Passenger passenger = getPassengerFromRequest(request);
        return passenger;
    }

    private PassengerRequest getPassengerRequestFromAPI(UUID passengerHash) {
        ClientResponse response = apiCaller.requestPassenger(passengerHash.toString());
        validateResponse(response, passengerHash);
        return response.getEntity(PassengerRequest.class);
    }

    private void validateResponse(ClientResponse response, UUID passengerHash) {
        if (response.getStatus() != OK.getStatusCode())
            throw new PassengerNotFoundException(passengerHash);
    }

    private Passenger getPassengerFromRequest(PassengerRequest request) {
        return passengerAssembler.toDomain(request);
    }
}
