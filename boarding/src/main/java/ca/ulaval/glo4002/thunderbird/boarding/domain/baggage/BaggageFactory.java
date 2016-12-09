package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

public class BaggageFactory {

    public Baggage createBaggage(String type) {
        switch (type) {
            case "checked":
                BaggageCheckedFactory()
                break;
        }

    }
}
