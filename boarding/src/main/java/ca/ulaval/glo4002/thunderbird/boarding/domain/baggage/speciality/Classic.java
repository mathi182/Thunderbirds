package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Embeddable;

@Embeddable
public class Classic implements Speciality {
    private static final String SPECIALITY_NAME = "CLASSIC";

    @Override
    public float getModificator() {
        return 0;
    }

    @Override
    public String getSpecialityName() {
        return SPECIALITY_NAME;
    }
}
