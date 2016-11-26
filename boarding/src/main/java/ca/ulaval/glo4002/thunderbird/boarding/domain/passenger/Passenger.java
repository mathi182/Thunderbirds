package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy.SeatClassBaggageStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy.SeatClassBaggageTemplate;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Passenger {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID passengerHash;
    private Seat.SeatClass seatClass;
    private Instant flightDate;
    private java.lang.String flightNumber;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Baggage> baggages;

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, java.lang.String flightNumber,
                     List<Baggage> baggages) {
        this(passengerHash, seatClass, flightDate, flightNumber);
        this.baggages = new ArrayList<>(baggages);
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, java.lang.String flightNumber) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.baggages = new ArrayList<>();
    }

    protected Passenger() {
        //for hibernate
    }

    public UUID getHash() {
        return passengerHash;
    }

    public boolean isSameSeatClass(Seat.SeatClass seatClass) {
        return seatClass.equals(seatClass);
    }

    public void addBaggage(Baggage baggage) {
        int baggageTypeCount = countBaggageOfType(baggage.getType());

        SeatClassBaggageStrategyFactory factory = new SeatClassBaggageStrategyFactory();
        SeatClassBaggageTemplate seatClassBaggageStrategy = factory.getStrategy(this.seatClass);
        seatClassBaggageStrategy.validateNewBaggage(baggage, baggageTypeCount);

        float newBaggagePrice = seatClassBaggageStrategy.calculateNewBaggagePrice(baggage, baggageTypeCount);

        baggage.setPrice(newBaggagePrice);
        baggages.add(baggage);
    }

    public int countBaggageOfType(String seatClass) {
        int count = 0;
        for (Baggage baggage : this.baggages) {
            if (baggage.getType().equals(seatClass)) {
                count += 1;
            }
        }
        return count;
    }

    public int getBaggageCount() {
        return baggages.size();
    }

    public List<Baggage> getBaggages() {
        return baggages;
    }

    public Instant getFlightDate() {
        return flightDate;
    }

    public java.lang.String getFlightNumber() {
        return flightNumber;
    }
}
