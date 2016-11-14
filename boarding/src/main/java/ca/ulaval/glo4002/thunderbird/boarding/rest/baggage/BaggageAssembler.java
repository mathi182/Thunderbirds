package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

public class BaggageAssembler {

    public BaggageDTO toDTO(Baggage baggage) {
        BaggageDTO baggageDTO = new BaggageDTO();

        baggageDTO.weight = baggage.getWeightInGrams();
        baggageDTO.linear_dimension = baggage.getDimensionInMm();
        baggageDTO.price = baggage.getPrice();

        return baggageDTO;
    }
}
