package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import java.util.List;

public interface PlaneRepository {
    Plane getPlaneInformation(String modelID);
    List<Seat> getSeats(String modelID);
}
