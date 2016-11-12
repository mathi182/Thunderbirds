package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterBaggageResponseBody {
    @JsonProperty("allowed")
    public boolean allowed;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("denied_reason")
    public String deniedReason;

    public RegisterBaggageResponseBody(boolean allowed, String deniedReason) {
        this.allowed = allowed;
        this.deniedReason = deniedReason;
    }

    public RegisterBaggageResponseBody(boolean allowed) {
        this.allowed = allowed;
    }
}
