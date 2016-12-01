package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static java.util.Optional.ofNullable;

public class PassengerService {
    private static final String DOMAIN_NAME = "localhost";
    private static final String PATH = "passengers";

    private static final String PORT_PROPERTY = "reservation.port";
    private static final int DEFAULT_PORT = 8787;

    public Passenger fetchPassenger(UUID passengerHash) {
        int httpPort = ofNullable(System.getProperty(PORT_PROPERTY)).map(Integer::parseInt).orElse(DEFAULT_PORT);
        String url = String.format("http://%s:%d/%s/%s", DOMAIN_NAME, httpPort, PATH, passengerHash);

        ClientResponse response = getResource(url);
        validateResponse(response);

        PassengerDTO passengerDTO = response.getEntity(PassengerDTO.class);
        PassengerAssembler passengerAssembler = new PassengerAssembler();
        return passengerAssembler.toDomain(passengerDTO);
    }

    private ClientResponse getResource(String url) {
        return Client.create().resource(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
    }

    private void validateResponse(ClientResponse response) {
        if (response.getStatus() != OK.getStatusCode()) {
            throw new PassengerNotFoundException();
        }
    }
}