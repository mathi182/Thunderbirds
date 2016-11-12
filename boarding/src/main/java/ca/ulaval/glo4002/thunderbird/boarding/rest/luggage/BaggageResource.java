package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Random;
import java.util.UUID;

@Path("/passengers/{passenger_hash}/baggages")
@Produces(MediaType.APPLICATION_JSON)
public class BaggageResource {
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerBaggage(RegisterLuggageRequest request, @PathParam("passenger_hash") String passengerHash) {
        Passenger passenger = getPassenger(UUID.fromString(passengerHash));
        Luggage luggage = convertRequestToBaggage(request);

        //TODO: faire la validation du luggage ici
        passenger.addBaggage(luggage);

        int id = new Random().nextInt(Integer.MAX_VALUE);
        String baggageRegistrationIdString = String.valueOf(id);
        URI uri = buildLocationUri(baggageRegistrationIdString);

        RegisterLuggageResponseBody baggageResponseBody = new RegisterLuggageResponseBody(true);
        return Response.created(uri).entity(baggageResponseBody).build();
    }

    private Luggage convertRequestToBaggage(RegisterLuggageRequest request) {
        RegisterLuggageRequestAssembler registerBagageRequestAssembler = new RegisterLuggageRequestAssembler();
        return registerBagageRequestAssembler.getDomainLuggage(request);
    }

    private URI buildLocationUri(String baggageHash) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        return uriBuilder.path(baggageHash).build();
    }

    private Passenger getPassenger(UUID passengerHash) {
        PassengerRepository repository = ServiceLocator.resolve(PassengerRepository.class);
        return repository.getPassenger(passengerHash);
    }
}
