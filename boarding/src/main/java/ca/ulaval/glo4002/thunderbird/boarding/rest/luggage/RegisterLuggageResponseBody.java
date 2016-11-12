package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterLuggageResponseBody {
    @JsonProperty("allowed")
    public boolean allowed;

    @JsonProperty("refusation_reason")
    public String refusationReason;

    public RegisterLuggageResponseBody(boolean allowed, String refusationReason) {
        this.allowed = allowed;
        this.refusationReason = refusationReason;
    }

    public RegisterLuggageResponseBody(boolean allowed) {
        this.allowed = allowed;
    }
}
