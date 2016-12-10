package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Embeddable;

@Embeddable
public class Overweight extends Speciality {
    private final String SPECIALITY_NAME = "OUTSTANDING";

    @Override
    public float getSpecialityFee() {
        return 0;
    }

    @Override
    public String getSpecialityName() {
        return SPECIALITY_NAME;
    }
}
