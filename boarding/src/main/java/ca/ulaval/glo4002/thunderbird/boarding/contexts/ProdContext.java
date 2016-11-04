package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.InMemoryFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneRepositoryProvider;

public class ProdContext implements Context {
    @Override
    public void apply() {
        AMSSystemFactory amsSystemFactory = new AMSSystemFactory();
        AMSSystem amsSystem = amsSystemFactory.create();
        PlaneRepositoryProvider planeRepositoryProvider = new PlaneRepositoryProvider();
        PlaneRepository planeRepository = planeRepositoryProvider.getRepository();
        FlightRepository flightRepository = new InMemoryFlightRepository(amsSystem, planeRepository);
        new FlightRepositoryProvider().setFlightRepository(flightRepository);
    }
}
