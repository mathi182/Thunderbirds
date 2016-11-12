package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterBaggageResponseBody {
    @JsonProperty("allowed")
    public boolean allowed;

    @JsonProperty("refusation_reason")
    public String refusationReason;

    public RegisterBaggageResponseBody(boolean allowed, String refusationReason) {
        this.allowed = allowed;
        this.refusationReason = refusationReason;
    }

    public RegisterBaggageResponseBody(boolean allowed) {
        this.allowed = allowed;
    }
}
