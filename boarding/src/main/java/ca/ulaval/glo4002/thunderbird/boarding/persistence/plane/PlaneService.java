package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.Seat;

import java.util.List;

public interface PlaneService {
    Plane getPlaneInformation(String modelID);
    List<Seat> getSeats(String modelID);
}
