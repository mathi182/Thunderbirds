package ca.ulaval.glo4002.thunderbird.app;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationServer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread boardingThread = new Thread(new BoardingServer());
        Thread resevationThread = new Thread(new ReservationServer());

        boardingThread.start();
        resevationThread.start();

        boardingThread.join();
        resevationThread.join();
    }
}