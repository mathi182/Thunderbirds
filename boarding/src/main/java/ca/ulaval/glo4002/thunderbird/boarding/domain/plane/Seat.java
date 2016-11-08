package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seat {

    public enum SeatClass { ANY, ECONOMY, BUSINESS }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int rowNumber;
    private String seatName;
    private int legRoom;
    private boolean hasWindow;
    private boolean haveClearView;
    private double price;
    private SeatClass seatClass;
    private boolean isExitRow;
    private boolean isAvailable;

    public Seat(int rowNumber, String seatName, int legRoom, boolean hasWindow, boolean haveClearView, double price,
                SeatClass seatClass, boolean isExitRow, boolean isAvailable) {
        this.rowNumber = rowNumber;
        this.seatName = seatName;
        this.legRoom = legRoom;
        this.hasWindow = hasWindow;
        this.haveClearView = haveClearView;
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

    public int getRow() {
        return rowNumber;
    }

    public String getSeatName() {
        return seatName;
    }

    public double getPrice() {
        return price;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public int getLegRoom() {
        return legRoom;
    }

    public boolean hasMoreLegRoomThan(int legRoomToCompare) {
        return legRoom > legRoomToCompare;
    }

    public boolean hasSameAmountOfLegRoom(int currentMostLegRoom) {
        return legRoom == currentMostLegRoom;
    }

    public boolean hasLowerPrice(Seat seat) {
        return price < seat.getPrice();
    }
}
