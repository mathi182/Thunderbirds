package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationsResource {

    @GET
    @Path("/{reservation_number}")
    public Response fetchReservation(@PathParam("reservation_number") int reservationNumber) {
        try {
            Reservation reservation = Reservation.findByReservationNumber(reservationNumber);
            if (reservation.isValid()) {

                return Response.ok(reservation, MediaType.APPLICATION_JSON).build();
            }
            return Response.status(NOT_FOUND).build();
        } catch (ReservationNotFoundException e) {
            return Response.status(NOT_FOUND).build();
        }
    }
}
