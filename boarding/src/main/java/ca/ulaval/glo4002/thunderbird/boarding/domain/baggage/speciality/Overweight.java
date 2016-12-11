package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Entity;

@Entity
public class Overweight extends Speciality {
    private final String SPECIALITY_NAME = "OVERWEIGHT";
    private final float SPECIALITY_FEE = 1.1f;

    public Overweight() {
        id = SPECIALITY_NAME;
    }

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
        if (!(obj instanceof Overweight)) {
            return false;
        }
        Speciality other = (Speciality) obj;
        return SPECIALITY_NAME.equals(other.getSpecialityName());
    }
}
