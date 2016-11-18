package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaggageDTO {

    @JsonProperty("weight")
    public int weight;

    @JsonProperty("linear_dimension")
    public int linear_dimension;

    @JsonProperty("price")
    public float price;

    public BaggageDTO() {
    }

    public BaggageDTO(int weight, int linear_dimension, float price) {
        this.weight = weight;
        this.linear_dimension = linear_dimension;
        this.price = price;
    }
}
