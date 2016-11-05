package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import org.junit.Test;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits.cm;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits.po;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits.kg;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits.lbs;

public class CheckedBaggageEconomyTest {
    @Test
    public void givenValidCheckedBaggage_whenValidate_shouldDoNothing() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(cm, 10, kg, 10);

        checkedBaggage.validate();
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void givenInvalidKgWeightCheckedBaggage_whenValidate_shouldThrowBaggageWeightInvalidException() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(cm, 10, kg, 24);

        checkedBaggage.validate();
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void givenInvalidLbsWeightCheckedBaggage_whenValidate_shouldThrowBaggageWeightInvalidException() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(cm, 10, lbs, 51);

        checkedBaggage.validate();
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void givenInvalidCmDimensionCheckedBaggage_whenValidate_shouldThrowBaggageDimensionInvalidException() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(cm, 159, lbs, 51);

        checkedBaggage.validate();
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void givenInvalidPoDimensionCheckedBaggage_whenValidate_shouldThrowBaggageDimensionInvalidException() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(po, 63, lbs, 51);

        checkedBaggage.validate();
    }
}