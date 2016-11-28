package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/passengers/{passenger_hash}/baggages")
@Produces(MediaType.APPLICATION_JSON)
public class BaggageResource {
    @Context
    UriInfo uriInfo;

    private PassengerRepository repository;

    public BaggageResource() {
        this.repository = ServiceLocator.resolve(PassengerRepository.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerBaggage(RegisterBaggageDTO request, @PathParam("passenger_hash") UUID passengerHash) {
        Passenger passenger = repository.getPassenger(passengerHash);
        Baggage baggage = convertRequestToBaggage(request);
        passenger.addBaggage(baggage);

        String baggageId = baggage.getId().toString();
        URI uri = uriInfo.getAbsolutePathBuilder().path(baggageId).build();
        RegisterBaggageResponse baggageResponseBody = new RegisterBaggageResponse(true);
        return Response.created(uri).entity(baggageResponseBody).build();
    }

    @GET
    public Response getBaggagesList(@PathParam("passenger_hash") UUID passengerHash) {
        Passenger passenger = repository.getPassenger(passengerHash);
        BaggagesListDTO baggagesListDTO = getBaggagesListDTOFromPassenger(passenger);
        return Response.ok(baggagesListDTO, MediaType.APPLICATION_JSON).build();
    }

    private BaggagesListDTO getBaggagesListDTOFromPassenger(Passenger passenger) {
        List<Baggage> baggages = passenger.getBaggages();
        float price = passenger.calculateBaggagesPrice();

        return new BaggagesListAssembler().toDTO(price, baggages);
    }

    private Baggage convertRequestToBaggage(RegisterBaggageDTO request) {
        RegisterBaggageAssembler registerBaggageRequestAssembler = new RegisterBaggageAssembler();
        return registerBaggageRequestAssembler.getDomainBaggage(request);
    }
}