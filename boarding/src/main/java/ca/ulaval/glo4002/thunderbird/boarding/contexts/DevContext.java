package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerRequest;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler;

public class DevContext implements Context {
    @Override
    public void apply() {
        registerFlightRepository();
        registerPassengerRepository();
    }

    private FlightRepository registerFlightRepository() {
        AMSSystem amsSystem = new AMSSystemFactory().create();

        PlaneService planeService = new PlaneServiceGlo3000();
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeService);
        new FlightRepositoryProvider().setFlightRepository(flightRepository);

        return flightRepository;
    }

    private PassengerRepository registerPassengerRepository() {
        PassengerAssembler assembler = new PassengerAssembler();
        PassengerRequest request = new PassengerRequest();
        PassengerService service = new PassengerService(assembler,request);
        PassengerRepository passengerRepository = new HibernatePassengerRepository(service);
        new PassengerRepositoryProvider().setPassengerRepository(passengerRepository);
        return passengerRepository;
    }
}
