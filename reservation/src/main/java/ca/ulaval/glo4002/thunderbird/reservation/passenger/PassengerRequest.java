package ca.ulaval.glo4002.thunderbird.reservation.passenger;


import java.util.UUID;

public class PassengerRequest {

    private UUID passengerID;

    PassengerRequest(UUID passengerID){
        this.passengerID = passengerID;
    }

    public UUID getPassengerID() {
        return passengerID;
    }
}
