package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoSuchStrategyException;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class SeatAssignationRequestAssemblerTest {
    private static final String INVALID_ASSIGNATION_MODE = "invalidMode";
    private static final String RANDOM_ASSIGNATION_MODE = "RANDOM";
    private static final String CHEAPEST_ASSIGNATION_MODE = "CHEAPEST";
    private static final UUID PASSENGER_HASH = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

    private SeatAssignationRequestAssembler assemblerTest;
    private SeatAssignationRequest requestTest;

    @Before
    public void setup(){
        assemblerTest = new SeatAssignationRequestAssembler();
        requestTest = new SeatAssignationRequest();
    }

    @Test
    public void givenRandomAssignationModeSeatAssignationRequest_whenGetMode_ShouldReturnRandomMode(){
        requestTest.mode = RANDOM_ASSIGNATION_MODE;
        requestTest.passengerHash = PASSENGER_HASH;

        SeatAssignationStrategy.assignMode actualValue = assemblerTest.getMode(requestTest);

        SeatAssignationStrategy.assignMode expectedValue = SeatAssignationStrategy.assignMode.RANDOM;
        assertEquals(expectedValue,actualValue);
    }

    @Test(expected = NoSuchStrategyException.class)
    public void givenInvalidAssignationModeSeatAssignationRequest_whenGetMode_ShouldThrowNoSuchStrategyException(){
        requestTest.mode = INVALID_ASSIGNATION_MODE;
        requestTest.passengerHash = PASSENGER_HASH;

        assemblerTest.getMode(requestTest);
    }

    @Test
    public void givenCheapesAssignationModeSeatAssignationRequest_whenGetMode_ShouldReturnCheapestMode(){
        requestTest.mode = CHEAPEST_ASSIGNATION_MODE;
        requestTest.passengerHash = PASSENGER_HASH;

        SeatAssignationStrategy.assignMode actualValue = assemblerTest.getMode(requestTest);

        SeatAssignationStrategy.assignMode expectedValue = SeatAssignationStrategy.assignMode.CHEAPEST;
        assertEquals(expectedValue,actualValue);
    }

}