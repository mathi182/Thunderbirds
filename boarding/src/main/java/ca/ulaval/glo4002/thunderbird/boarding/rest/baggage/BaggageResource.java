package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Path("/passengers/{passenger_hash}/baggages")
@Produces(MediaType.APPLICATION_JSON)
public class BaggageResource {
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerBaggage(RegisterBaggageRequest request, @PathParam("passenger_hash") String passengerHash) {
        Passenger passenger = getPassenger(UUID.fromString(passengerHash));
        Baggage baggage = convertRequestToBaggage(request);

        //TODO: faire la validation du baggage ici
        passenger.addBaggage(baggage);

        int id = new Random().nextInt(Integer.MAX_VALUE);
        String baggageRegistrationIdString = String.valueOf(id);
        URI uri = buildLocationUri(baggageRegistrationIdString);

        RegisterBaggageResponseBody baggageResponseBody = new RegisterBaggageResponseBody(true);
        return Response.created(uri).entity(baggageResponseBody).build();
    }

    @GET
    public Response getBaggagesList(@PathParam("passenger_hash") String passengerHash) {
        try {
            Passenger passenger = getPassenger(UUID.fromString(passengerHash));
            BaggagesListDTO baggagesListDTO = getBaggagesListDTOFromPassenger(passenger);

            return Response.ok(baggagesListDTO, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private BaggagesListDTO getBaggagesListDTOFromPassenger(Passenger passenger) {
        List<Baggage> baggages = passenger.getBaggages();
        BaggagesListAssembler baggagesListAssembler = new BaggagesListAssembler();

        return baggagesListAssembler.toDTO(baggages);
    }

    private Baggage convertRequestToBaggage(RegisterBaggageRequest request) {
        RegisterBaggageRequestAssembler registerBagageRequestAssembler = new RegisterBaggageRequestAssembler();
        return registerBagageRequestAssembler.getDomainBaggage(request);
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
