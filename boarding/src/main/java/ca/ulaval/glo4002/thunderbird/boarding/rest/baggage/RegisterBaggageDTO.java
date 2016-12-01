package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterBaggageDTO {
    @JsonProperty("linear_dimension")
    public int linearDimension;

    @JsonProperty("linear_dimension_unit")
    public String linearDimensionUnit;

    @JsonProperty("weight")
    public int weight;

    @JsonProperty("weight_unit")
    public String weightUnit;

    @JsonProperty("type")
    public String type;

    public RegisterBaggageDTO() {
        // Use for deserialization
    }

    public RegisterBaggageDTO(String linearDimensionUnit, int linearDimension, String weightUnit,
                              int weight, String type) {
        this.linearDimensionUnit = linearDimensionUnit;
        this.linearDimension = linearDimension;
        this.weightUnit = weightUnit;
        this.weight = weight;
        this.type = type;
    }
}