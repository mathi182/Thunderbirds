package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Embeddable;

@Embeddable
public class Sport extends Speciality {
    private final String SPECIALITY_NAME = "SPORT";
    private final float SPECIALITY_FEE = 1.25f;

    @Override
    public String getSpecialityName() {
        return SPECIALITY_NAME;
    }

    @Override
    public float getSpecialityFee() {
        return SPECIALITY_FEE;
    }
}
