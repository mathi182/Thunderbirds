package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

public class PassengerRepositoryProvider {
    private static ThreadLocal<PassengerRepository> instance = new ThreadLocal<>();

    public void setPassengerRepository(PassengerRepository repository) {
        instance.set(repository);
    }

    public PassengerRepository getPassengerRepository() {
        return instance.get();
    }
}
