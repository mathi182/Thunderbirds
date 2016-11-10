package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterBaggageRequest {
    @JsonProperty("linear_dimension")
    public Integer linearDimension;

    @JsonProperty("linear_dimension_unit")
    public String linearDimensionUnit;

    @JsonProperty("weightInG")
    public Integer weight;

    @JsonProperty("weight_unit")
    public String weightUnit;

    @JsonProperty("type")
    public String type;

    public RegisterBaggageRequest() {
    }

    public RegisterBaggageRequest(String linearDimensionUnit, Integer linearDimension, String weightUnit,
                                  Integer weight,  String type) {
        this.linearDimensionUnit = linearDimensionUnit;
        this.linearDimension = linearDimension;
        this.weightUnit = weightUnit;
        this.weight = weight;
        this.type = type;
    }
}
