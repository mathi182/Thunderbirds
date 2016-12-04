package fixtures;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import java.time.Instant;
import java.util.UUID;
import static org.junit.Assert.assertEquals;

public class PassengerFixture extends HibernateBaseFixture {
    private static final Instant FLIGHT_DATE = Instant.now();
    private static final double DELTA = 0.01;

    private PassengerRepository repository;

    public PassengerFixture() {
        this.repository = new HibernatePassengerRepository(new PassengerService());
    }

    public void createPassenger(UUID passengerHash, String flightNumber, Seat.SeatClass seatClass) {
        withEntityManager((tx) -> {
            Passenger passenger = new Passenger(passengerHash, seatClass, FLIGHT_DATE, flightNumber, false, true);
            repository.savePassenger(passenger);
        });
    }

    public void addBaggageToPassengerWithPassengerHash(Baggage baggage, UUID passengerHash) {
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
