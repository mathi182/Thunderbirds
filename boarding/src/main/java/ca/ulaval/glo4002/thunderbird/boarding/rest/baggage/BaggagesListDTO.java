package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class BaggagesListDTO {

    @JsonProperty("total_price")
    public float total_price;

    @JsonProperty("baggages")
    public List<BaggageDTO> baggages = new ArrayList<>();

    public BaggagesListDTO() {
    }

    public BaggagesListDTO(float total_price, List<BaggageDTO> baggages) {
        this.total_price = total_price;
        this.baggages = baggages;
    }
}
