package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.ElementNotFoundException;

public class PlaneNotFoundException extends ElementNotFoundException {
    public PlaneNotFoundException(String modelID) {
        super("plane " + modelID);
    }
}
