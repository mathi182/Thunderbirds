package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.BaggageApplicationService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.UUID;

@Path("/passengers/{passenger_hash}/baggages")
@Produces(MediaType.APPLICATION_JSON)
public class BaggageResource {

    @Context
    UriInfo uriInfo;

    private BaggageApplicationService baggageApplicationService;
    private RegisterBaggageResponseBody registerBaggageResponseBody;

    public BaggageResource(){
        this.baggageApplicationService = new BaggageApplicationService();
        this.registerBaggageResponseBody = new RegisterBaggageResponseBody(true);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerBaggage(RegisterBaggageRequest request, @PathParam("passenger_hash") UUID passengerHash) {
        baggageApplicationService.registerBaggage(passengerHash, request);

        URI uri = buildURI();

        RegisterBaggageResponseBody baggageResponseBody = registerBaggageResponseBody;
        return Response.created(uri).entity(baggageResponseBody).build();
    }

    @GET
    public Response getBaggagesList(@PathParam("passenger_hash") String passengerHash) {
        BaggagesListDTO baggagesListDTO = baggageApplicationService.getBaggagesListDTOFromPassenger(passengerHash);

        return Response.ok(baggagesListDTO, MediaType.APPLICATION_JSON).build();
    }

    private URI buildURI() {
        UUID id = UUID.randomUUID();

        return buildLocationUri(id.toString());
    }

    private URI buildLocationUri(String baggageHash) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        return uriBuilder.path(baggageHash).build();
    }
}
