package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterBaggageResponseBody {
    @JsonProperty("allowed")
    public boolean allowed;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("refusation_reason")
    public String refusation_reason;

    public RegisterBaggageResponseBody(boolean allowed, String refusation_reason) {
        this.allowed = allowed;
        this.refusation_reason = refusation_reason;
    }

    public RegisterBaggageResponseBody(boolean allowed) {
        this.allowed = allowed;
    }
}
