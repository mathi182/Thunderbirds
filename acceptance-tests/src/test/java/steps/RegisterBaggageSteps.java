package steps;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import contexts.AcceptanceContext;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;
import fixtures.PassengerFixture;
import helpers.MeasureConverter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RegisterBaggageSteps implements Fr {

    private static final UUID passengerHash = UUID.randomUUID();
    private static final String BAGGAGE_TYPE = "type";
    private static final String BAGGAGE_MASS = "poids";
    private static final String BAGGAGE_LENGTH = "taille";

    private PassengerFixture passengerFixture;

    @Before
    public void setUp() throws Exception {
        new AcceptanceContext().apply();
        passengerFixture = new PassengerFixture();
    }

    public RegisterBaggageSteps() {
        Étantdonné("^un passager Bob ayant une réservation en classe économique sur le vol AC-(\\d+)$", (Integer flightNumber) -> {
            String flightNumberAsString = "AC-" + flightNumber.toString();
            passengerFixture.givenAPassenger(passengerHash, flightNumberAsString,
                    Seat.SeatClass.ECONOMY);
        });

        Étantdonné("^qu'il a déjà un bagage enregistré respectant les normes$", () -> {
            passengerFixture.givenABaggageForPassenger(passengerHash);
        });

        Quand("^il enregistre le bagage suivant :$", (DataTable dataTable) -> {
            List<Map<String, Object>> dataTableAsMaps = dataTable.asMaps(String.class, Object.class);
            Map<String, Object> baggageTable = dataTableAsMaps.get(0);
            String type = (String) baggageTable.get(BAGGAGE_TYPE);

            String massAsString = (String) baggageTable.get(BAGGAGE_MASS);
            Mass mass = MeasureConverter.getMassFromString(massAsString);

            String lengthAsString = (String) baggageTable.get(BAGGAGE_LENGTH);
            Length length = MeasureConverter.getLengthFromString(lengthAsString);

            Baggage baggage = new CheckedBaggage(length, mass, type);
            passengerFixture.addBaggageToPassenger(passengerHash, baggage);
        });

        Alors("^son total a payer est de (\\d+)\\$$", (Integer expectedTotalBaggagePrice) -> {
            passengerFixture.thenTotalBaggagePriceEquals(passengerHash, expectedTotalBaggagePrice);
        });
    }
}
