package ca.ulaval.glo4002.thunderbird.boarding.domain;

public class Baggages implements Identity {
    public String passengerHash;

    @Override
    public String getId() {
        return passengerHash;
    }
}
