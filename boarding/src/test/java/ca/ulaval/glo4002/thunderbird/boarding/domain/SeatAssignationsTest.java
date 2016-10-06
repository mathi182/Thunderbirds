package ca.ulaval.glo4002.thunderbird.boarding.domain;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerStorage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

public class SeatAssignationsTest {

    String VALID_PASSENGER_HASH = "1203dsa2s";

    @PrepareForTest
    public void preparation() {
        PowerMockito.mockStatic(PassengerStorage.class);
    }

    @Before
    public void setUp() {

    }

    @Test
    @Ignore
    public void givenAValidPassengerHash_whenAssigningASeat_shouldGetAPassenger(){
        PowerMockito.doThrow(new PassengerNotFoundException(VALID_PASSENGER_HASH))
                .when(PassengerStorage.findByPassengerHash(VALID_PASSENGER_HASH));


    }

}