package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Checked")
public class CheckedBaggage extends Baggage {
    public CheckedBaggage(int linearDimension, int weight) {
        super(linearDimension, weight);
    }
}
