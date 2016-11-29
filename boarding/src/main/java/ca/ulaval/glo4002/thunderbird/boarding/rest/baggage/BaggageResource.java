package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.BaggageApplicationService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

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

    private final BaggageApplicationService baggageApplicationService;
    private final BaggagesListAssembler baggagesListAssembler;
    private final RegisterBaggageAssembler registerBaggageAssembler;

    public BaggageResource() {
        this.baggageApplicationService = new BaggageApplicationService();
        this.baggagesListAssembler = new BaggagesListAssembler();
        this.registerBaggageAssembler = new RegisterBaggageAssembler();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerBaggage(RegisterBaggageDTO request, @PathParam("passenger_hash") UUID passengerHash) {
        Baggage baggage = registerBaggageAssembler.getDomainBaggage(request);
        UUID baggageId = baggageApplicationService.registerBaggage(passengerHash, baggage);

        URI uri = uriInfo.getAbsolutePathBuilder().path(baggageId.toString()).build();
        RegisterBaggageResponse registerBaggageResponse = RegisterBaggageResponse.accepted();

        return Response.created(uri).entity(registerBaggageResponse).build();
    }

    @GET
    public Response getBaggagesList(@PathParam("passenger_hash") UUID passengerHash) {
        Passenger passenger = baggageApplicationService.getPassenger(passengerHash);
        List<Baggage> baggages = passenger.getBaggages();
        float price = passenger.calculateBaggagesPrice();
        BaggagesListDTO baggagesListDTO = baggagesListAssembler.toDTO(price, baggages);

        return Response.ok(baggagesListDTO, MediaType.APPLICATION_JSON).build();
    }
}