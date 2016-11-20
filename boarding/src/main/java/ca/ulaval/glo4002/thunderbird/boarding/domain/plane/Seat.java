package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.expceptions.SeatAlreadyTakenException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seat {

    public enum SeatClass {
        ANY,
        ECONOMY,
        BUSINESS,
        BIG_FRONT,
        PREMIUM_ECONOMY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int rowNumber;
    private String seatName;
    private int legRoom;
    private boolean hasWindow;
    private boolean hasClearView;
    private double price;
    private SeatClass seatClass;
    private boolean isExitRow;
    private boolean isAvailable;

    public Seat(int rowNumber, String seatName, int legRoom, boolean hasWindow, boolean hasClearView, double price,
                SeatClass seatClass, boolean isExitRow, boolean isAvailable) {
        this.rowNumber = rowNumber;
        this.seatName = seatName;
        this.legRoom = legRoom;
        this.hasWindow = hasWindow;
        this.hasClearView = hasClearView;
        this.price = price;
        this.seatClass = seatClass;
        this.isExitRow = isExitRow;
        this.isAvailable = isAvailable;
    }

    protected Seat() {
        // for hibernate
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void take() {
        if (!isAvailable) {
            throw new SeatAlreadyTakenException();
        }
        isAvailable = false;
    }

    //TODO make seat compare itself with a seat, not with an int
    public boolean hasMoreLegRoomThan(int legRoomToCompare) {
        return legRoom > legRoomToCompare;
    }

    //TODO refactor this. Cheapest seat should not be chosen here
    public Seat bestSeatViewBetween(Seat seat) {
        if (hasBetterViewThan(seat)) {
            return this;
        }
        if (seat.hasBetterViewThan(this)) {
            return seat;
        }
        if (hasLowerPriceThan(seat)) {
            return this;
        }
        return seat;
    }

    public boolean hasSameViewAs(Seat comparedSeat) {
        return hasWindow == comparedSeat.hasWindow && hasClearView == comparedSeat.hasClearView;
    }

    public boolean hasBetterViewThan(Seat comparedSeat) {
        if (hasSameViewAs(comparedSeat)) {
            return false;
        }

        if (hasWindow != comparedSeat.hasWindow)
            return hasWindow;
        else {
            return hasClearView;
        }
    }

    public boolean hasSameAmountOfLegRoom(int currentMostLegRoom) {
        return legRoom == currentMostLegRoom;
    }

    public boolean hasLowerPriceThan(Seat seat) {
        return price < seat.getPrice();
    }

    public int getRow() {
        return rowNumber;
    }

    public String getSeatName() {
        return seatName;
    }

    public double getPrice() {
        return price;
    }

    public int getLegRoom() {
        return legRoom;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }
}
