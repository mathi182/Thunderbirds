package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import com.sun.jersey.api.client.ClientResponse;

import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;

public class PassengerService {
    private PassengerAssembler passengerAssembler;
    private PassengerRequest passengerRequest;

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