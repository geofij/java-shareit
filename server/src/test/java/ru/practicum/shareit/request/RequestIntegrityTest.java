package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Transactional
@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestIntegrityTest {
    private final RequestService service;
    private final UserService userService;
    private final EntityManager em;


    @Test
    void save() {
        User user = User.builder().email("user@user.user").name("user").build();
        UserResponseDto saveUser = userService.add(user);

        RequestCreateDto requestDto = RequestCreateDto.builder()
                .description("request")
                .build();

        RequestResponseDto savedRequest = service.create(requestDto, saveUser.getId());

        TypedQuery<ItemRequest> query = em.createQuery(
                "Select r from ItemRequest r where r.description = :description", ItemRequest.class);
        ItemRequest ir = query.setParameter("description", requestDto.getDescription()).getSingleResult();

        Assertions.assertEquals(requestDto.getDescription(), ir.getDescription());

    }
}
