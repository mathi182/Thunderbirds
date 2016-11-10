package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.List;

public class PlaneServiceGlo3000 implements PlaneService {
    private static final String PLANE_SERVICE_LOCATION = "http://glo3000.ift.ulaval.ca/plane-blueprint";
    private static final String PLANE_INFORMATION_PATH_FORMAT = "/planes/%1s.json";
    private static final String SEATS_INFORMATION_PATH_FORMAT = "/planes/%1s/seats.json";

    @Override
    public Plane getPlaneInformation(String modelID) {
        String url = PLANE_SERVICE_LOCATION + String.format(PLANE_INFORMATION_PATH_FORMAT, modelID);
        ClientResponse response = getResource(url);
        verifyResponse(response, modelID);

        PlaneDTO dto = response.getEntity(PlaneDTO.class);
        return new PlaneAssembler().toDomain(dto);
    }

    private ClientResponse getResource(String url) {
        Client client = Client.create();
        WebResource resource = client.resource(url);
        return resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    }

    private void verifyResponse(ClientResponse response, String modelID) {
        if (response.getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
            throw new PlaneNotFoundException(modelID);
        } else if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed to retrieve JSON with status : " + response.getStatus());
        }
    }

    @Override
    public List<Seat> getSeats(String modelID) {
        String url = PLANE_SERVICE_LOCATION + String.format(SEATS_INFORMATION_PATH_FORMAT, modelID);
        ClientResponse response = getResource(url);
        verifyResponse(response, modelID);

        SeatsInformationDTO dto = response.getEntity(SeatsInformationDTO.class);
        List<Seat> seats = new SeatsAssembler().toDomain(dto);

        return seats;
    }
}
