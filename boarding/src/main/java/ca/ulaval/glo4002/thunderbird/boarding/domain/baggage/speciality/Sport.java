package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Embeddable;

@Embeddable
public class Sport implements Speciality {
    private static final String SPECIALITY_NAME = "SPORT";
    private final float modificator = 1.25f;

    @Override
    public String getSpecialityName() {
        return SPECIALITY_NAME;
    }

    @Override
    public float getModificator() {
        return modificator;
    }
}
