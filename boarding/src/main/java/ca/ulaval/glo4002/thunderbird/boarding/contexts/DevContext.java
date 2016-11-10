package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerFetcher;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAPICaller;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler;

import java.util.UUID;

public class DevContext implements Context {
    public static UUID EXISTENT_PASSENGER_HASH;

    @Override
    public void apply() {
        AMSSystemFactory amsSystemFactory = new AMSSystemFactory();
        AMSSystem amsSystem = amsSystemFactory.create();
        PlaneRepositoryProvider planeRepositoryProvider = new PlaneRepositoryProvider();
        PlaneRepository planeRepository = planeRepositoryProvider.getRepository();
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeRepository);
        new FlightRepositoryProvider().setFlightRepository(flightRepository);

        PassengerAPICaller apiCaller = new PassengerAPICaller();
        PassengerAssembler assembler = new PassengerAssembler();
        PassengerFetcher fetcher = new PassengerFetcher(assembler, apiCaller);
        PassengerRepository passengerRepository = new HibernatePassengerRepository(fetcher);
        new PassengerRepositoryProvider().setPassengerRepository(passengerRepository);
    }

    private Passenger createPassengerInRepository(PassengerRepository passengerRepository) {
        UUID passengerHash = UUID.randomUUID();
        Seat.SeatClass seatClass = Seat.SeatClass.ECONOMY;

        Passenger passenger = new Passenger(passengerHash, seatClass);
        passengerRepository.savePassenger(passenger);

        return passenger;
    }
}
