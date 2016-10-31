package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;

import java.util.ArrayList;
import java.util.Arrays;

public class DevContext implements Context {
    public final static int EXISTANT_RESERVATION_NUMBER = 100;
    public static String EXISTANT_PASSENGER_HASH;

    @Override
    public void apply() {
        Passenger passenger = createPassenger();
        EXISTANT_PASSENGER_HASH = passenger.getId();
        passenger.save();

        Reservation reservation = createReservation(passenger);
        reservation.save();
    }

    private Passenger createPassenger() {
        int reservationNumber = EXISTANT_RESERVATION_NUMBER;
        String firstName = "firstName";
        String lastName = "lastName";
        int age = 100;
        String passportNumber = "passportNumber";
        String seatClass = "seatClass";

        return new Passenger(reservationNumber, firstName, lastName, age, passportNumber, seatClass);
    }

    private Reservation createReservation(Passenger passenger) {
        int number = EXISTANT_RESERVATION_NUMBER;
        String date = "2016-01-31";
        String confirmation = "A3833";
        String payment = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
        String flightNumber = "AC1765";
        String flightDate = "2016-09-06T13:00:00Z";
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        return new Reservation(number, date, confirmation, flightNumber, flightDate, payment, passengers);
    }
}