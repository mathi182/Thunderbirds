package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Embeddable;

@Embeddable
public class Oversize extends Speciality {
    private final String SPECIALITY_NAME = "OVERSIZE";
    private final float SPECIALITY_FEE = 1.1f;

    @Override
    public float getSpecialityFee() {
        return SPECIALITY_FEE;
    }

    @Override
    public String getSpecialityName() {
        return SPECIALITY_NAME;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Oversize)) {
            return false;
        }
        Speciality other = (Speciality) obj;
        return SPECIALITY_NAME.equals(other.getSpecialityName());
    }
}
