package steps;

import contexts.boarding.BoardingContext;
import contexts.reservation.ReservationContext;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;
import fixtures.boarding.SeatAssigmentFixture;
import fixtures.reservation.RestCheckinFixture;

public class SeatAssignmentSteps implements Fr {
    private SeatAssigmentFixture seatAssignmentFixture;
    private RestCheckinFixture checkinFixture;

    @Before
    public void beforeScenario() throws Throwable {
        new ReservationContext().apply();
        new BoardingContext().apply();
        seatAssignmentFixture = new SeatAssigmentFixture();
        checkinFixture = new RestCheckinFixture();
    }

    public SeatAssignmentSteps() {
        Étantdonné("^un passage Alice ayant une réservation à bord d'un vol$", () -> {
            throw new PendingException();
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