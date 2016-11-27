package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class AMSSystemFactoryTest {

    @Test
    public void whenCreatingNewAMSSystem_shouldReturnNewAMSSystem() {
        AMSSystemFactory factory = new AMSSystemFactory();
        AMSSystem amsSystem = factory.create();

        assertThat(amsSystem, instanceOf(AMSSystem.class));
    }
}
