package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerRequest;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class DevContext implements Context {
    public static UUID EXISTENT_BOARDING_PASSENGER_HASH;

    @Override
    public void apply() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);

        registerFlightRepository();
        registerPassengerRepository();
        fillDatabase();

        EntityManagerProvider.clearEntityManager();
        entityManager.close();
    }

    private void registerFlightRepository() {
        AMSSystem amsSystem = new AMSSystemFactory().create();
        PlaneService planeService = new PlaneServiceGlo3000();
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeService);

        ServiceLocator.registerSingleton(FlightRepository.class, flightRepository);
    }

    private void registerPassengerRepository() {
        PassengerAssembler assembler = new PassengerAssembler();
        PassengerRequest request = new PassengerRequest();
        PassengerService service = new PassengerService(assembler, request);
        PassengerRepository passengerRepository = new HibernatePassengerRepository(service);

        ServiceLocator.registerSingleton(PassengerRepository.class, passengerRepository);
    }

    private void fillDatabase() {
        Passenger passenger = createPassenger();
        PassengerRepository repository = ServiceLocator.resolve(PassengerRepository.class);
        repository.savePassenger(passenger);
        EXISTENT_BOARDING_PASSENGER_HASH = passenger.getHash();
    }

    private Passenger createPassenger() {
        UUID passengerHash = UUID.randomUUID();
        Seat.SeatClass seatClass = Seat.SeatClass.ECONOMY;
        Instant flightDate = Instant.ofEpochMilli(new Date().getTime());
        String flightNumber = "QK-918";
        boolean vip = false;

        return new Passenger(passengerHash, seatClass, flightDate, flightNumber, vip);
    }
}

