package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaggageDTO {
    @JsonProperty("weight")
    public int weight;
    @JsonProperty("linear_dimension")
    public int linear_dimension;
    @JsonProperty("price")
    public float price;
}
