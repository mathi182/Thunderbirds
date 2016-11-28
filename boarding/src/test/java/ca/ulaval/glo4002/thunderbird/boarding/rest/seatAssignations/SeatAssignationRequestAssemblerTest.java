package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeatAssignationRequestAssemblerTest {
    private static final String INVALID_ASSIGNATION_MODE = "invalidMode";
    private static final String RANDOM_ASSIGNATION_MODE = "RANDOM";
    private static final String CHEAPEST_ASSIGNATION_MODE = "CHEAPEST";
    private static final String MOST_LEG_ROOM_ASSIGNATION_MODE = "LEGS";
    private static final String LANDSCAPE_ASSIGNATION_MODE = "LANDSCAPE";

    private SeatAssignationAssembler assemblerTest;
    private SeatAssignationDTO requestTest;

    @Before
    public void setup() {
        assemblerTest = new SeatAssignationAssembler();
        requestTest = new SeatAssignationDTO();
    }

    @Test
    public void givenRandomAssignationModeSeatAssignationRequest_whenGettingMode_shouldReturnRandomMode() {
        requestTest.mode = RANDOM_ASSIGNATION_MODE;

        SeatAssignationStrategy.AssignMode actualValue = assemblerTest.getMode(requestTest);

        SeatAssignationStrategy.AssignMode expectedValue = SeatAssignationStrategy.AssignMode.RANDOM;
        assertEquals(expectedValue, actualValue);
    }

    @Test(expected = NoSuchStrategyException.class)
    public void givenInvalidAssignationModeSeatAssignationRequest_whenGettingMode_shouldThrowNoSuchStrategyException() {
        requestTest.mode = INVALID_ASSIGNATION_MODE;

        assemblerTest.getMode(requestTest);
    }

    @Test
    public void givenCheapestAssignationModeSeatAssignationRequest_whenGettingMode_shouldReturnCheapestMode() {
        requestTest.mode = CHEAPEST_ASSIGNATION_MODE;

        SeatAssignationStrategy.AssignMode actualValue = assemblerTest.getMode(requestTest);

        SeatAssignationStrategy.AssignMode expectedValue = SeatAssignationStrategy.AssignMode.CHEAPEST;
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void givenLandscapeAssignationModeSeatAssignationRequest_whenGettingMode_shouldReturnLandscapeMode() {
        requestTest.mode = LANDSCAPE_ASSIGNATION_MODE;

        SeatAssignationStrategy.AssignMode actualValue = assemblerTest.getMode(requestTest);

        SeatAssignationStrategy.AssignMode expectedValue = SeatAssignationStrategy.AssignMode.LANDSCAPE;
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenMostLegRoomAssignationModeSeatAssignationRequest_whenGettingMode_shouldReturnMostLegRoomMode() {
        requestTest.mode = MOST_LEG_ROOM_ASSIGNATION_MODE;

        SeatAssignationStrategy.AssignMode actualValue = assemblerTest.getMode(requestTest);

        SeatAssignationStrategy.AssignMode expectedValue = SeatAssignationStrategy.AssignMode.LEGS;
        assertEquals(expectedValue, actualValue);
    }
}