package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneRepositoryProvider;

public class DevContext implements Context {
    @Override
    public void apply() {
        AMSSystemFactory amsSystemFactory = new AMSSystemFactory();
        AMSSystem amsSystem = amsSystemFactory.create();
        PlaneRepositoryProvider planeRepositoryProvider = new PlaneRepositoryProvider();
        PlaneRepository planeRepository = planeRepositoryProvider.getRepository();
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeRepository);
        new FlightRepositoryProvider().setFlightRepository(flightRepository);
    }

    // TODO: créer un passenger avec l'api
    //private Passenger createPassenger() {
    //
    //}
}
