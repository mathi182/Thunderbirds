package contexts.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.contexts.Context;
import fixtures.reservation.ReservationServerRunner;

public class ReservationContext implements Context {
    @Override
    public void apply() {
        new ReservationServerRunner().startJettyServer();
    }
}