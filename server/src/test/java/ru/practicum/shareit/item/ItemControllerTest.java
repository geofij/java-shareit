package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ItemController.class)

class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemService service;

    private final ItemCreateDto itemIn = ItemCreateDto.builder()
            .name("item")
            .description("item")
            .available(true)
            .build();

    private final CommentCreateDto commentIn = CommentCreateDto.builder()
            .text("comment")
            .build();
    private final CommentCreatedResponseDto commentOut = CommentCreatedResponseDto.builder()
            .id(1L)
            .text("comment")
            .authorName("user")
            .build();

    private final ItemResponseDto itemOut1 = ItemResponseDto.builder()
            .name("item")
            .description("item")
            .available(true)
            .build();

    private final ItemResponseWithBookingAndCommentDto itemOut2 = ItemResponseWithBookingAndCommentDto.builder()
            .name("item2")
            .description("item2")
            .available(true)
            .build();


    @Test
    void shouldSave() throws Exception {
        when(service.save(any(ItemCreateDto.class), anyLong())).thenReturn(itemOut1);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemIn))
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(itemIn.getDescription())));
    }

    @Test
    void shouldGetById() throws Exception {
        when(service.getById(anyLong(), anyLong())).thenReturn(itemOut2);

        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("item2")));
    }

    @Test
    void shouldUpdate() throws Exception {
        Item modelItem = ItemMapper.toItem(itemIn, User.builder()
                        .id(1L)
                        .name("user")
                        .email("user@user.user")
                        .build());

        when(service.update(any(Item.class), any(Item.class))).thenReturn(itemOut1);
        when(service.getById(anyLong())).thenReturn(modelItem);

        mvc.perform(patch("/items/1")
                        .content(mapper.writeValueAsString(itemIn))
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("item")));

        mvc.perform(patch("/items/1")
                        .content(mapper.writeValueAsString(itemIn))
                        .header("X-Sharer-User-Id", "2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetList() throws Exception {
        when(service.getAllOwnerItems(anyLong(), anyInt(), anyInt())).thenReturn(List.of(itemOut2));

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSearch() throws Exception {
        when(service.searchByText(anyString(), anyInt(), anyInt())).thenReturn(List.of(itemOut1));

        mvc.perform(get("/items/search")
                        .param("text", "item")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddComment() throws Exception {
        when(service.createNewComment(any(CommentCreateDto.class), anyLong(), anyLong())).thenReturn(commentOut);

        mvc.perform(post("/items/1/comment")
                        .content(mapper.writeValueAsString(commentIn))
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}