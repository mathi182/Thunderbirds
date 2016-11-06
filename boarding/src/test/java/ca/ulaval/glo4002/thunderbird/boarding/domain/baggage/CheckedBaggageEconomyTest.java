package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import org.junit.Test;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits.CM;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits.PO;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits.KG;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits.LBS;

public class CheckedBaggageEconomyTest {
    @Test
    public void givenValidCheckedBaggage_whenValidate_shouldDoNothing() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(CM, 10, KG, 10);

        checkedBaggage.validate();
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void givenInvalidKgWeightCheckedBaggage_whenValidate_shouldThrowBaggageWeightInvalidException() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(CM, 10, KG, 24);

        checkedBaggage.validate();
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void givenInvalidLbsWeightCheckedBaggage_whenValidate_shouldThrowBaggageWeightInvalidException() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(CM, 10, LBS, 51);

        checkedBaggage.validate();
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void givenInvalidCmDimensionCheckedBaggage_whenValidate_shouldThrowBaggageDimensionInvalidException() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(CM, 159, LBS, 51);

        checkedBaggage.validate();
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void givenInvalidPoDimensionCheckedBaggage_whenValidate_shouldThrowBaggageDimensionInvalidException() throws Exception {
        CheckedBaggage checkedBaggage = new CheckedBaggageEconomy(PO, 63, LBS, 51);

        checkedBaggage.validate();
    }
}