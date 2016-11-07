package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.PassengerNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PassengerRepositoryImplTest {
    private static final UUID RANDOM_UUID = UUID.randomUUID();

    private PassengerRepository passengerRepositoryTest;

    @Before
    public void setup(){
        passengerRepositoryTest = new PassengerRepositoryImpl();
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenEmptyRepository_whenGettingPassenger_shouldThrowException(){
        passengerRepositoryTest.getPassenger(RANDOM_UUID);
    }

    @Test
    @Ignore //TODO put this test in a border test
    public void givenEmptyRepository_whenSavingPassenger_shouldBeSavedCorrectly(){

    }
}