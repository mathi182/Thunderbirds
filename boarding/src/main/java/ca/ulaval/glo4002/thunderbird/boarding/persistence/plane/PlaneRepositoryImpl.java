package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.List;

public class PlaneRepositoryImpl implements PlaneRepository {
    private static final String PLANE_SERVICE_LOCATION = "http://glo3000.ift.ulaval.ca/plane-blueprint";
    private static final String PLANE_INFORMATION_PATH_FORMAT = "/planes/%1s.json";
    private static final String SEATS_INFORMATION_PATH_FORMAT = "/planes/%1s/seats.json";

    @Override
    public Plane getPlaneInformation(String modelID) {
        String url = PLANE_SERVICE_LOCATION + String.format(PLANE_INFORMATION_PATH_FORMAT, modelID);
        Client client = Client.create();
        WebResource resource = client.resource(url);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
            throw new PlaneNotFoundException(modelID);
        } else if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed to retrieve JSON at : " + url + "\n With status : " + response.getStatus());
        }

        PlaneDTO dto = response.getEntity(PlaneDTO.class);
        Plane plane = new PlaneAssembler().toDomain(dto);
        return plane;
    }

    @Override
    public List<Seat> getSeats(String modelID) {
        String url = PLANE_SERVICE_LOCATION + String.format(SEATS_INFORMATION_PATH_FORMAT, modelID);
        Client client = Client.create();
        WebResource resource = client.resource(url);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
            throw new PlaneNotFoundException(modelID);
        } else if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed to retrieve JSON at : " + url + "\n With status : " + response.getStatus());
        }

        SeatsInformationDTO dto = response.getEntity(SeatsInformationDTO.class);
        List<Seat> seats = new SeatsAssembler().toDomain(dto);

        return seats;
    }
}
