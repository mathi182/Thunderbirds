package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAPICaller;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerDTO;
import com.sun.jersey.api.client.ClientResponse;

import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;

public class PassengerFetcher {

    private PassengerAssembler passengerAssembler;
    private PassengerAPICaller apiCaller;

    public PassengerFetcher(PassengerAssembler passengerAssembler, PassengerAPICaller apiCaller) {
        this.passengerAssembler = passengerAssembler;
        this.apiCaller = apiCaller;
    }

    public Passenger fetchPassenger(UUID PassengerHash) {
        PassengerDTO request = getPassengerRequestFromAPI(PassengerHash);
        Passenger passenger = getPassengerFromRequest(request);
        return passenger;
    }

    private PassengerDTO getPassengerRequestFromAPI(UUID passengerHash) {
        ClientResponse response = apiCaller.requestPassenger(passengerHash.toString());
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
}
