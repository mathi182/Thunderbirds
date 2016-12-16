package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Plane {
    @EmbeddedId
    private PlaneId planeId;
    private int numberSeats;
    private int cargoWeight;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    public Plane(PlaneId planeId, int numberOfSeats, int cargoWeight, List<Seat> seats) {
        this.planeId = planeId;
        this.numberSeats = numberOfSeats;
        this.cargoWeight = cargoWeight;
        this.seats = seats;
    }

    protected Plane() {
        // for hibernate
    }

    public PlaneId getId() {
        return planeId;
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }

    public Mass getMaximumCargoWeight() {
        return Mass.fromKilograms(cargoWeight);
    }
}
