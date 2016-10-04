package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.ReservationRessources;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * Created by alexandre on 2016-09-17.
 */
@Path("/checkins")
@Produces(MediaType.APPLICATION_JSON)
public class CheckinResource {
    private final String FIELDS_REQUIRED_MESSAGE = "by and passengerHas fields are required";
    private final String PASSENGER_RESERVATION_NOT_FOUND_MESSAGE = "passenger reservation not found";
    private final String PASSENGER_RESERVATION_NOT_VALID = "passenger information missing in the reservation. full name and passport number fields are required.";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkin(Checkin checkin) {
        if (!checkin.isAgentIdValid() || !checkin.isPassengerHashValid()) {
            return Response.status(BAD_REQUEST).entity(Entity.json(FIELDS_REQUIRED_MESSAGE)).build();
        }

        String passengerHash = checkin.getPassengerHash();
        Passenger passengerFound = findCheckinPassenger(passengerHash);
        if (passengerFound == null) {
            return Response.status(NOT_FOUND).entity(Entity.json(PASSENGER_RESERVATION_NOT_FOUND_MESSAGE)).build();
        }

        if (!passengerFound.isValidForCheckin()) {
            return Response.status(BAD_REQUEST).entity(Entity.json(PASSENGER_RESERVATION_NOT_VALID)).build();
        }

        String checkinId = checkin.getCheckinId();
        checkin.save();
        return Response.created(URI.create("/checkins/" + checkinId)).build();
    }

    public Passenger findCheckinPassenger(String passengerHash) {
        Passenger passengerFound = Passenger.findByPassengerHash(passengerHash);
        ReservationsResource reservationRessources = new ReservationsResource();
        // TODO: Valider le réservation number ! et changer les tests de checkin évidemment
        /**
            Pour myriam et alexis :D
         Response fetchReservationResponse = reservationRessources.fetchReservation(passengerFound.reservationNumber);
         fetchReservationResponse.getEntity();
        **/
        return passengerFound;
    }
}
