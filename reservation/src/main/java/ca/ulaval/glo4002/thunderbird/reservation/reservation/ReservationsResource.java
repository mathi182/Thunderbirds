package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path(ReservationsResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationsResource {
    public static final String PATH = "/reservations/";

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{reservation_number}")
    public Response fetchReservation(@PathParam("reservation_number") int reservationNumber) {
        Reservation reservation = Reservation.findByReservationNumber(reservationNumber);
        return Response.ok(reservation, MediaType.APPLICATION_JSON).build();
    }

}