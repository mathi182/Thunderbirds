package fixtures;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PassengerFixture extends HibernateBaseFixture {
    private static final double DELTA = 0.01;
    private static final int DIMENSION_VALUE = 11;
    private static final Length LINEAR_DIMENSION = Length.fromMillimeters(DIMENSION_VALUE);
    private static final int WEIGHT_VALUE = 22;
    private static final Mass WEIGHT = Mass.fromGrams(WEIGHT_VALUE);
    private static final Flight NO_FLIGHT = null;

    private PassengerRepository repository;

    public PassengerFixture() {
        this.repository = new HibernatePassengerRepository(new PassengerService());
    }

    public void givenAPassenger(UUID passengerHash, String flightNumber, Seat.SeatClass seatClass) {
        withEntityManager((tx) -> {
            Passenger passenger = new Passenger(passengerHash, seatClass, false, true, false, NO_FLIGHT);
            repository.savePassenger(passenger);
        });
    }

    public void givenABaggageForPassenger(UUID passengerHash) {
        Baggage baggage = new CheckedBaggage(LINEAR_DIMENSION, WEIGHT);
        addBaggageToPassenger(passengerHash, baggage);
    }

    public void addBaggageToPassenger(UUID passengerHash, Baggage baggage) {
        withEntityManager((tx) -> {
            Passenger passenger = repository.findByPassengerHash(passengerHash);
            passenger.addBaggage(baggage);
            repository.savePassenger(passenger);
        });
    }

    public void thenTotalBaggagePriceEquals(UUID passengerHash, float totalPriceExpected) {
        withEntityManager((tx) -> {
            Passenger passenger = repository.findByPassengerHash(passengerHash);
            float totalPriceActual = passenger.calculateBaggagesPrice();
            assertEquals(totalPriceExpected, totalPriceActual, DELTA);
        });
    }
}
