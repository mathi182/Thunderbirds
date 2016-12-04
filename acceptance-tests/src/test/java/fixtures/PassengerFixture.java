package fixtures;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;

import javax.persistence.EntityTransaction;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

public class PassengerFixture extends HibernateBaseFixture {
    public static final UUID PASSENGER_HASH = UUID.randomUUID();
    public static final Instant FLIGHT_DATE = Instant.now();
    public static final String FLIGHT_NUMBER = "flight_number";
    private HibernatePassengerRepository repository;

    public PassengerFixture() {
        this.repository = new HibernatePassengerRepository(new PassengerService());
    }

    public void givenAPassengerInReservation(String flightNumber, Seat.SeatClass seatClass) {
        withEntityManager((Consumer<EntityTransaction>) (tx) -> {
            tx.begin();
            Passenger passenger = new Passenger(PASSENGER_HASH, seatClass, FLIGHT_DATE, flightNumber, false, true);
            repository.savePassenger(passenger);
            tx.commit();
        });
    }
}
