package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;

public class FlightRepositoryProvider {
    private static ThreadLocal<FlightRepository> instance = new ThreadLocal<>();

    public void setFlightRepository(FlightRepository repository) {
        instance.set(repository);
    }

    public FlightRepository getFlightRepository() {
        return instance.get();
    }
}