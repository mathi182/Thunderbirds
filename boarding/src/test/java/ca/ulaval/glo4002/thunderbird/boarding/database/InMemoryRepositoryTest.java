package ca.ulaval.glo4002.thunderbird.boarding.database;

import ca.ulaval.glo4002.thunderbird.boarding.domain.Identity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

public class InMemoryRepositoryTest {
    private final String ID_NON_EXISTENT = "IdNonExistent";

    @Spy
    InMemoryRepository<Identity> repository;
    @Spy
    Identity elementA, elementB;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenWeFindAllElements_shouldReturnAnEmptyCollection() {
        Collection allElements = repository.findAll();
        assertTrue(allElements.isEmpty());
    }

    @Test
    public void givenWeAddAnElement_whenWeFindAllElements_shouldNotReturnAnEmptyCollection() {
        repository.save(elementA);
        Collection allElements = repository.findAll();
        assertFalse(allElements.isEmpty());
    }

    @Test
    public void givenWeAddATwoElements_whenWeFindAllElements_shouldReturnTwoElements() {
        repository.save(elementA);
        repository.save(elementB);

        Collection allElements = repository.findAll();

        assertEquals(2, allElements.size());
    }

    @Test
    public void givenWeAddSameElementTwice_whenWeFindAllElements_shouldBePresentOnce() {
        repository.save(elementA);
        repository.save(elementA);

        Collection allElements = repository.findAll();

        assertEquals(1, allElements.size());
    }

    @Test
    public void whenWeFindNonExistentElement_shouldNotBePresent() {
        Optional<Identity> savedElement = repository.findById(ID_NON_EXISTENT);
        assertFalse(savedElement.isPresent());
    }

    @Test
    public void givenWeAddAnElement_whenWeFindThisElement_shouldBePresent() {
        repository.save(elementA);
        Optional<Identity> saveElement = repository.findById(elementA.id);
        assertTrue(saveElement.isPresent());
    }

    @Test
    public void givenWeAddAnElement_whenWeRemoveThisElement_shouldNotBePresent() {
        repository.save(elementA);
        repository.remove(elementA);
        assertFalse(repository.findById(elementA.id).isPresent());
    }

    @Test
    public void givenWeAddAnElement_whenWeRemoveAnotherElement_shouldBePresent() {
        repository.save(elementA);
        repository.remove(elementB);
        assertTrue(repository.findById(elementA.id).isPresent());
    }
}
