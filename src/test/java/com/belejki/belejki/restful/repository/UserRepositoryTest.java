package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("test@mail.bg", "TestPesho", "TestPeshev", "1q2w3e");
        user.setEnabled(true);
        user.setLastLogin(LocalDate.now());
        userRepository.save(user);
    }

    @Test
    public void testCreateUser() {
        User newUser = new User("newUser@abv.bg", "Alice", "Smith", "3e2w1q");
        userRepository.save(newUser);

        Optional<User> retrievedUser = userRepository.findById("newUser@abv.bg");
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getUsername()).isEqualTo("newUser@abv.bg");
        assertThat(retrievedUser.get().getFirstName()).isEqualTo("Alice");
    }
}