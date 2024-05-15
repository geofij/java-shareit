package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.config.AppConfig;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class UserRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
        assertNotNull(em);
    }

    @Test
    void shouldSave() {
        User user = User.builder()
                .name("user")
                .email("user@user.ru")
                .build();

        Assertions.assertNull(user.getId());

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser.getId());
    }
}