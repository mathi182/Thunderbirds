package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Embeddable;

@Embeddable
public class Sport implements Speciality {
    private final float modificator = 1.25f;

    @Override
    public float getModificator() {
        return modificator;
    }
}
