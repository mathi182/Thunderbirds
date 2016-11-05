package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterBaggageRequest {
    @JsonProperty("linear_dimension")
    public Integer linearDimension;

    @JsonProperty("linear_dimension_unit")
    public String linearDimensionUnit;

    @JsonProperty("weight")
    public Integer weight;

    @JsonProperty("weight_unit")
    public String weightUnit;

    @JsonProperty("type")
    public String type;

    public RegisterBaggageRequest() {
    }
}
