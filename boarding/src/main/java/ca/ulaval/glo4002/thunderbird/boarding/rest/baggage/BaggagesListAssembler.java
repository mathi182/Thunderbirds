package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

import java.util.List;

public class BaggagesListAssembler {

    public BaggagesListDTO toDTO(List<Baggage> baggages) {
        BaggageAssembler baggageAssembler = new BaggageAssembler();
        BaggagesListDTO baggagesListDTO = new BaggagesListDTO();
        float totalPrice = 0f;

        for (Baggage baggage : baggages) {
            BaggageDTO baggageDTO = baggageAssembler.toDTO(baggage);
            baggagesListDTO.baggages.add(baggageDTO);
            totalPrice += baggage.getPrice();
        }
        baggagesListDTO.total_price = totalPrice;

        return baggagesListDTO;
    }
}
