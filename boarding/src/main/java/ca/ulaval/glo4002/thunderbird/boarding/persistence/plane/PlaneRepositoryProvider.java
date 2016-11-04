package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;

public class PlaneRepositoryProvider {

    public PlaneRepository getRepository() {
        return new PlaneRepositoryImpl();
    }
}
