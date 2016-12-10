package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Embeddable;

@Embeddable
public class Overweight extends Speciality {
    private final String SPECIALITY_NAME = "OVERWEIGHT";
    private final float SPECIALITY_FEE = 1.1f;

    @Override
    public float getSpecialityFee() {
        return SPECIALITY_FEE;
    }

    @Override
    public String getSpecialityName() {
        return SPECIALITY_NAME;
    }
}
