package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

public class PlaneRepositoryProvider {

    public PlaneRepository getRepository() {
        return new PlaneRepositoryImpl();
    }
}
