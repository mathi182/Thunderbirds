package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String planeModel;
    private int numberSeats;
    private int cargoWeight;

    protected Plane() {

    }

    public Plane(String model, int numberOfSeats, int cargoWeight) {
        this.planeModel = model;
        this.numberSeats = numberOfSeats;
        this.cargoWeight = cargoWeight;
    }
}
