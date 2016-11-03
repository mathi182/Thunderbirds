package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

public class Seat {
    private int rowNumber;
    private String seat;
    private int legRoom;
    private boolean hasWindow;
    private boolean haveClearView;
    private double price;
    private String priceClass;
    private boolean isExitRow;
    private boolean isTaken;

    public Seat(int rowNumber, String seat, int legRoom, boolean hasWindow, boolean haveClearView, double price, String priceClass, boolean isExitRow, boolean isTaken) {
        this.rowNumber = rowNumber;
        this.seat = seat;
        this.legRoom = legRoom;
        this.hasWindow = hasWindow;
        this.haveClearView = haveClearView;
        this.price = price;
        this.priceClass = priceClass;
        this.isExitRow = isExitRow;
        this.isTaken = isTaken;
    }

    public boolean isAvailable() {
        return !isTaken;
    }

    public void take() {
        if (isTaken) {
            throw new SeatAlreadyTakenException();
        }
        isTaken = true;
    }

    public int getRow() {
        return rowNumber;
    }

    public String getSeat() {
        return seat;
    }
}
