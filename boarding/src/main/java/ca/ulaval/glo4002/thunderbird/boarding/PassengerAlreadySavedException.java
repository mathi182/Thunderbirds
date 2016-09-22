package ca.ulaval.glo4002.thunderbird.boarding;

/**
 * Created by antoine on 16-09-22.
 */
public class PassengerAlreadySavedException extends RuntimeException {
    String passengerHash;

    PassengerAlreadySavedException(String passengerHash) {
        this.passengerHash = passengerHash;
    }
}
