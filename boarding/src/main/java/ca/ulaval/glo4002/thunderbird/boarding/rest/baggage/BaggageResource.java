package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;

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
    public Response registerBaggage(RegisterBaggageRequest request, @PathParam("passenger_hash") UUID passengerHash) {
        Passenger passenger = getPassenger(passengerHash);
        Baggage baggage = convertRequestToBaggage(request);

        validateBaggage(baggage, passenger);
        passenger.addBaggage(baggage);

        URI uri = buildURI();

        RegisterBaggageResponseBody baggageResponseBody = new RegisterBaggageResponseBody(true);
        return Response.created(uri).entity(baggageResponseBody).build();
    }

    private URI buildURI() {
        int id = new Random().nextInt(Integer.MAX_VALUE);
        String baggageRegistrationIdString = String.valueOf(id);

        return buildLocationUri(baggageRegistrationIdString);
    }

    private void validateBaggage(Baggage baggage, Passenger passenger) {
        BaggageValidationStrategyFactory factory = new BaggageValidationStrategyFactory();
        BaggageValidationStrategy strategy = factory.getStrategy(passenger.getSeatClass());
        strategy.validateBaggage(baggage);
    }

    @GET
    public Response getBaggagesList(@PathParam("passenger_hash") String passengerHash) {
            Passenger passenger = getPassenger(UUID.fromString(passengerHash));
            BaggagesListDTO baggagesListDTO = getBaggagesListDTOFromPassenger(passenger);
            return Response.ok(baggagesListDTO, MediaType.APPLICATION_JSON).build();
    }

    private Passenger getPassenger(UUID passengerHash) {
        PassengerRepository repository = ServiceLocator.resolve(PassengerRepository.class);
        return repository.getPassenger(passengerHash);
    }


    private BaggagesListDTO getBaggagesListDTOFromPassenger(Passenger passenger) {
        List<Baggage> baggages = passenger.getBaggages();
        return new BaggagesListAssembler().toDTO(baggages);
    }

    private Baggage convertRequestToBaggage(RegisterBaggageRequest request) {
        RegisterBaggageRequestAssembler registerBaggageRequestAssembler = new RegisterBaggageRequestAssembler();
        return registerBaggageRequestAssembler.getDomainBaggage(request);
    }

    private URI buildLocationUri(String baggageHash) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        return uriBuilder.path(baggageHash).build();
    }

}
