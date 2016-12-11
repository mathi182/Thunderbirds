package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Speciality {

    @Id
    protected String id;

    public abstract float getSpecialityFee();
    public abstract String getSpecialityName();
    public abstract boolean equals(Object obj);
}
