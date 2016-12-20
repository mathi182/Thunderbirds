package ca.ulaval.glo4002.thunderbird.boarding.services;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.exceptions.PlaneNotFoundException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PlaneServiceGlo3000BorderTest {

    private String validPlaneModel = "dash-8";
    private String invalidPlaneModel = "asimfsa";

    private PlaneService planeService = new PlaneServiceGlo3000();

    @Test
    public void givenAValidPlaneId_whenGettingPlaneFromService_shouldObtainAPlane() {
        PlaneId validPlaneId = new PlaneId(validPlaneModel);

        Plane actualPlane = planeService.getPlane(validPlaneId);

        assertNotNull(actualPlane);
    }

    @Test(expected = PlaneNotFoundException.class)
    public void givenAnInvalidPlaneId_whenGettingPlaneFromService_shouldThrowAnException() {
        PlaneId invalidPlaneId = new PlaneId(invalidPlaneModel);

        planeService.getPlane(invalidPlaneId);
    }

}