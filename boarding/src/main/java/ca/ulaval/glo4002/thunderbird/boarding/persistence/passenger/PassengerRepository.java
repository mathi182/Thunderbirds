package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import java.util.UUID;

public interface PassengerRepository {
    Passenger getPassenger(UUID passengerHash);
    void savePassenger(Passenger passenger);
}
