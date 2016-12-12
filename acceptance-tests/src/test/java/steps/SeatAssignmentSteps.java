package steps;

import contexts.AcceptanceContext;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;
import fixtures.BaggageFixture;
import fixtures.PassengerFixture;

import java.util.UUID;

public class SeatAssignmentSteps implements Fr {
    private PassengerFixture passengerFixture;


    @Before
    public void setUp() throws Exception {
        new AcceptanceContext().apply();
        passengerFixture = new PassengerFixture();
    }

    public SeatAssignmentSteps() {
        Étantdonné("^un passage Alice ayant une réservation à bord d'un vol$", () -> {
        });

        Étantdonné("^que des places sont disponibles sur ce vol$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Étantdonné("^qu'elle a fait son checkin$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Quand("^elle procède à l'assignation de siège en choisissant le mode aléatoire$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Alors("^un siège lui a été assigné à bord de ce vol$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
