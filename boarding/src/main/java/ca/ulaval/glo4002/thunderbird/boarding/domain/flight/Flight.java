package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Flight {

    private String flightNumber;
    private Date flightDate;
    private List<Seat> seats;
    private Plane plane;

    public Flight(String flightNumber, Date flightDate, Plane plane, Collection<Seat> seats) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.plane = plane;
        this.seats = new ArrayList<>(seats);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Date getFlightDate() {
        return flightDate;
    }
}
