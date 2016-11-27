package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlaneTest {

    private static final String MODEL = "MODEL";
    private static final int NUMBER_OF_SEAT = 200;
    private static final int CARGO_WEIGHT = 1000;

    @Test
    public void shouldReturnRightValues() {
        Plane plane = new Plane(MODEL, NUMBER_OF_SEAT, CARGO_WEIGHT);

        assertEquals(MODEL, plane.getPlaneModel());
        assertEquals(NUMBER_OF_SEAT, plane.getNumberSeats());
        assertEquals(CARGO_WEIGHT, plane.getCargoWeight());
    }
}
