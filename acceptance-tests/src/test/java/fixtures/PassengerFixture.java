package fixtures;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.time.Instant;
import java.util.UUID;

public class PassengerFixture extends HibernateBaseFixture {
    private static final UUID PASSENGER_HASH = new UUID(1L, 3L);
    private static final Instant FLIGHT_DATE = Instant.now();

    private static final UUID BAGGAGE_HASH = new UUID(2L, 2L);
    private static final String TYPE = "Type";
    private static final int DIMENSION_VALUE = 11;
    private static final Length LINEAR_DIMENSION = Length.fromMillimeters(DIMENSION_VALUE);
    private static final int WEIGHT_VALUE = 22;
    private static final Mass WEIGHT = Mass.fromGrams(WEIGHT_VALUE);

    private PassengerRepository repository;

    public PassengerFixture() {
        this.repository = new HibernatePassengerRepository(new PassengerService());
    }

    public Passenger givenAPassengerInReservation(String flightNumber, Seat.SeatClass seatClass) {
        Passenger passenger = new Passenger(PASSENGER_HASH, seatClass, FLIGHT_DATE, flightNumber, false, true);

        withEntityManager((tx) -> {
            repository.savePassenger(passenger);
        });

        return passenger;
    }

    public Passenger addValidBaggageToPassenger(Passenger passenger) {
        passenger.addBaggage(new Baggage(BAGGAGE_HASH, LINEAR_DIMENSION, WEIGHT, TYPE));

        withEntityManager((tx) -> {
            repository.savePassenger(passenger);
        });

        return passenger;
    }
}
