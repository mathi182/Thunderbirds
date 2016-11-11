package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class RegisterBaggageRequest {

    @NotNull
    @JsonProperty("linear_dimension")
    public Integer linearDimension;

    @NotNull
    @JsonProperty("linear_dimension_unit")
    public String linearDimensionUnit;

    @NotNull
    @JsonProperty("weightInG")
    public Integer weight;

    @NotNull
    @JsonProperty("weight_unit")
    public String weightUnit;

    @NotNull
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
