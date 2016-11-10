package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler;

public class DevContext implements Context {
    @Override
    public void apply() {
        AMSSystemFactory amsSystemFactory = new AMSSystemFactory();
        AMSSystem amsSystem = amsSystemFactory.create();
        PlaneService planeService = new PlaneServiceGlo3000();
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeService);
        new FlightRepositoryProvider().setFlightRepository(flightRepository);

        PassengerAssembler assembler = new PassengerAssembler();
        PassengerService fetcher = new PassengerService(assembler);
        PassengerRepository passengerRepository = new HibernatePassengerRepository(fetcher);
        new PassengerRepositoryProvider().setPassengerRepository(passengerRepository);
    }
}
