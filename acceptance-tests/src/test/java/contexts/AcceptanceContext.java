package contexts;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CollectionFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;

public class AcceptanceContext implements context{

    @Override
    public void apply(){
        ServiceLocator.reset();
        ServiceLocator.registerSingleton(PassengerRepository.class, new HibernatePassengerRepository(new PassengerService()));
        BaggageFactory baggageFactory = new BaggageFactory();
        ServiceLocator.registerSingleton(BaggageFactory.class, baggageFactory);
        ServiceLocator.registerSingleton(CollectionFactory.class, new CollectionFactory());
    }
}
