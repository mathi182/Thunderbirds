package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import java.util.UUID;

public class Passenger {
    private UUID passengerHash;

    public Passenger(UUID passengerHash){
        this.passengerHash = passengerHash;
    }

    public UUID getHash() {
        return passengerHash;
    }
}
