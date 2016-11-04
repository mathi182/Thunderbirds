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
    private boolean isAvailable;

    public Seat(int rowNumber, String seat, int legRoom, boolean hasWindow, boolean haveClearView, double price, String priceClass, boolean isExitRow, boolean isAvailable) {
        this.rowNumber = rowNumber;
        this.seat = seat;
        this.legRoom = legRoom;
        this.hasWindow = hasWindow;
        this.haveClearView = haveClearView;
        this.price = price;
        this.priceClass = priceClass;
        this.isExitRow = isExitRow;
        this.isAvailable = isAvailable;
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

    public String getSeat() {
        return seat;
    }
}
