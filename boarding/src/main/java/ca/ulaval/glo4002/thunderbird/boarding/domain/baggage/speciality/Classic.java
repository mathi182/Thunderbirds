package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Embeddable;

@Embeddable
public class Classic implements Speciality {

    public final String identification = "CLASSIC";

    @Override
    public boolean equals(Object object) {
        if (object instanceof Classic) {
            return ((Classic) object).identification.equals(identification);
        }
        return false;
    }
}
