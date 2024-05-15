package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemIntegrityTest {
    private final ItemService service;
    private final UserService userService;
    private final EntityManager em;

    @Test
    void getAllSaved() {
        User user = User.builder()
                .email("rty@rty.rty")
                .name("rty")
                .build();
        UserResponseDto savedUser = userService.add(user);

        ItemCreateDto item1 = ItemCreateDto.builder()
                .name("qwe")
                .description("qwe")
                .available(true)
                .build();
        service.save(item1, savedUser.getId());

        ItemCreateDto item2 = ItemCreateDto.builder()
                .name("wer")
                .description("wer")
                .available(true)
                .build();
        service.save(item2, savedUser.getId());

        TypedQuery<Item> query = em.createQuery("select i from Item i where i.owner.id = :userId", Item.class);
        List<Item> items = query.setParameter("userId", savedUser.getId()).getResultList();

        Assertions.assertEquals(items.size(), 2);
        Assertions.assertEquals(items.get(0).getAvailable(), true);
    }
}
