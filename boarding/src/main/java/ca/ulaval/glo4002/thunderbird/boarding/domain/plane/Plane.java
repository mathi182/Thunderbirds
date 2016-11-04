package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

public class Plane {
    private String planeModel;
    private int numberSeats;
    private int cargoWeight;

    public Plane(String model, int numberOfSeats, int cargoWeight) {
        this.planeModel = model;
        this.numberSeats = numberOfSeats;
        this.cargoWeight = cargoWeight;
    }
}
