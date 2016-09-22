package ca.ulaval.glo4002.thunderbird.boarding;

/**
 * Created by antoine on 16-09-22.
 */
public class PassengerNotFoundException extends RuntimeException {
    String passengerHash;

    PassengerNotFoundException(String passengerHash) {
        this.passengerHash = passengerHash;
    }
}
