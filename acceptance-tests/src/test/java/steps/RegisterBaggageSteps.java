package steps;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.Fr;
import fixtures.PassengerFixture;
import org.junit.Before;

public class RegisterBaggageSteps implements Fr {

    private PassengerFixture passengerFixture;

    @Before
    public void setUp() throws Exception {
        passengerFixture = new PassengerFixture();
    }

    public RegisterBaggageSteps() {
        Étantdonné("^un passager Bob ayant une réservation en classe économique sur le vol AC-(\\d+)$", (Integer flightNumber) -> {
            // Write code here that turns the phrase above into concrete actions
            String flightNumberAsString = "AC-" + flightNumber.toString();
            passengerFixture.givenAPassengerInReservation(flightNumberAsString, Seat.SeatClass.ECONOMY);
            throw new PendingException();
        });

        Étantdonné("^qu'il a déjà un bagage enregistré respectant les normes$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Quand("^il enregistre le bagage suivant :$", (DataTable arg1) -> {
            // Write code here that turns the phrase above into concrete actions
            // For automatic transformation, change DataTable to one of
            // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
            // E,K,V must be a scalar (String, Integer, Date, enum etc)
            throw new PendingException();
        });

        Alors("^son total a payer est de (\\d+)\\$$", (Integer arg1) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
