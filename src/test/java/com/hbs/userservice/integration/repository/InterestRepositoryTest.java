package com.hbs.userservice.integration.repository;

import com.hbs.userservice.model.Interest;
import com.hbs.userservice.repository.InterestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class InterestRepositoryTest {

    @Autowired
    private InterestRepository interestRepository;

    @Test
    void whenCreateInterest_thenPersisted() {
        Interest interest = createInterest();
        Interest saved = interestRepository.save(interest);

        assertNotNull(saved.getId());
        assertEquals(interest.getName(), saved.getName());
    }

    @Test
    void whenFindById_thenReturnInterest() {
        Interest interest = interestRepository.save(createInterest());
        Optional<Interest> found = interestRepository.findById(interest.getId());

        assertTrue(found.isPresent());
        assertEquals(interest.getId(), found.get().getId());
    }

    @Test
    void whenUpdateInterest_thenChangesPersisted() {
        Interest interest = interestRepository.save(createInterest());
        String newName = "UpdatedInterest_" + UUID.randomUUID();
        interest.setName(newName);
        Interest updated = interestRepository.save(interest);

        assertEquals(newName, updated.getName());
    }

    @Test
    void whenDeleteInterest_thenRemoved() {
        Interest interest = interestRepository.save(createInterest());
        interestRepository.delete(interest);

        assertFalse(interestRepository.findById(interest.getId()).isPresent());
    }

    @Test
    void whenCreateDuplicateName_thenThrowsException() {
        String name = "UniqueInterest_" + UUID.randomUUID();
        Interest first = new Interest();
        first.setName(name);
        interestRepository.save(first);

        Interest duplicate = new Interest();
        duplicate.setName(name);

        assertThrows(DataIntegrityViolationException.class, () -> {
            interestRepository.saveAndFlush(duplicate);
        });
    }

    private Interest createInterest() {
        Interest interest = new Interest();
        interest.setName("Interest_" + UUID.randomUUID());
        return interest;
    }

}
