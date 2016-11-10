package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepositoryProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.UUID;

@Path("/passengers/{passenger_hash}/baggages")
@Produces(MediaType.APPLICATION_JSON)
public class BaggageRessource {
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerBaggage(RegisterBaggageRequest request, @PathParam("passenger_hash") String passengerHash) {
        //TODO: get passenger from the repository
        //TODO: validate the passenger baggage count limit
        Baggage baggage = convertRequestToBaggage(request);
        baggage.validate();
        //TODO: save the baggage in passenger repository from boarding
        URI uri = buildLocationUri("baggageHash");
        RegisterBaggageResponseBody baggageResponseBody = new RegisterBaggageResponseBody(true);
        return Response.created(uri).entity(baggageResponseBody).build();
    }

    private Baggage convertRequestToBaggage(RegisterBaggageRequest request) {
        RegisterBaggageRequestAssembler registerBagageRequestAssembler = new RegisterBaggageRequestAssembler();
        return registerBagageRequestAssembler.getDomainBaggage(request);
    }

    private URI buildLocationUri(String baggageHash) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        return uriBuilder.path(baggageHash).build();
    }

    private Passenger getPassenger(UUID passengerHash){
        PassengerRepositoryProvider provider = new PassengerRepositoryProvider();
        PassengerRepository repository = provider.getPassengerRepository();
        return repository.getPassenger(passengerHash);
    }
}
