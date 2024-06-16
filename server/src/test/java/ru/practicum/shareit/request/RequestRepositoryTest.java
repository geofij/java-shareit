package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.config.AppConfig;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.storage.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
public class RequestRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RequestRepository repository;

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

        User savedUser = userRepository.save(user);

        ItemRequest request = ItemRequest.builder()
                .requester(savedUser)
                .description("req")
                .build();


        assertNull(request.getId());

        ItemRequest savedRequest = repository.save(request);

        assertNotNull(savedRequest.getId());
    }
}