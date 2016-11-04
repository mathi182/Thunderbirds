package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;

public class PlaneAssembler {

    public Plane toDomain(PlaneDTO dto) {
        Plane plane = new Plane(dto.model, dto.numberOfSeats, dto.cargoWeight);
        return plane;
    }
}
