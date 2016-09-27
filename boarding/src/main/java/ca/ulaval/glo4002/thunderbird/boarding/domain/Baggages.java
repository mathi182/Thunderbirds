package ca.ulaval.glo4002.thunderbird.boarding.domain;

public class Baggages implements Identity {
    private String passengerHash;

    @Override
    public String getId() {
        return passengerHash;
    }
}
