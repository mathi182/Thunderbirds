package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.InMemoryFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneRepositoryProvider;

public class DevContext implements Context {
    @Override
    public void apply() {
        AMSSystem amsSystem = new AMSSystemFactory().create();
        PlaneRepository planeRepository = new PlaneRepositoryProvider().getRepository();
        new FlightRepositoryProvider().setFlightRepository(new InMemoryFlightRepository(amsSystem, planeRepository));
    }

    // TODO: créer un passenger avec l'api
    //private Passenger createPassenger() {
    //
    //}
}
