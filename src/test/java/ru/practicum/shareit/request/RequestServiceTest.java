package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.exception.DataNotFoundException;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.RequestServiceImpl;
import ru.practicum.shareit.request.storage.RequestRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RequestServiceTest {
    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private RequestServiceImpl service;

    @Test
    void getAllByRequester() {
        when(requestRepository.findAllByRequesterId(anyLong()))
                .thenReturn(List.of(ItemRequest.builder().id(1L).build()));

        var result = service.getAllUserRequests(1L);

        assertNotNull(result);
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void getRequestById() {
        ItemRequest request = ItemRequest.builder().id(1L).build();

        when(requestRepository.findById(1L)).thenReturn(Optional.ofNullable(request));
        when(itemRepository.findAllByRequestIdInOrderByIdDesc(anyList())).thenReturn(new ArrayList<>());

        var result = service.getRequestById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertThrows(DataNotFoundException.class, () -> service.getRequestById(2L));
    }

    @Test
    void getAll() {
        ItemRequest request = ItemRequest.builder().id(1L).build();

        when(requestRepository.findAllByRequesterIdNot(anyLong(), any(Pageable.class)))
                .thenReturn(Collections.singletonList(request));

        when(itemRepository.findAllByRequestIdInOrderByIdDesc(anyList())).thenReturn(new ArrayList<>());

        var result = service.getAllRequests(0, 1, 1L);

        assertNotNull(result);
    }
}