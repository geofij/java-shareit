package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemInBookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserInBookingDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookingService service;

    @MockBean
    private UserService userService;

    private final User owner = User.builder()
            .id(1L)
            .email("user@user.ru")
            .name("user")
            .build();
    private final User booker = User.builder()
            .id(2L)
            .email("booker@booker.ru")
            .name("booker")
            .build();
    private final Item item = Item.builder()
            .id(1L)
            .name("item")
            .description("item")
            .available(true)
            .owner(owner)
            .build();

    private final BookingCreateDto bookingIn = BookingCreateDto.builder()
            .start(LocalDateTime.now().plusHours(10))
            .end(LocalDateTime.now().plusHours(30))
            .itemId(1L)
            .build();

    private final BookingResponseDto bookingResponseDto = BookingResponseDto.builder()
            .id(1L)
            .start(bookingIn.getStart())
            .end(bookingIn.getEnd())
            .item(ItemInBookingDto.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .build())
            .booker(UserInBookingDto.builder()
                    .id(booker.getId())
                    .build())
            .status(BookingStatus.WAITING)
            .build();

    @Test
    void shouldSave() throws Exception {
        when(service.create(any(BookingCreateDto.class), anyLong())).thenReturn(bookingResponseDto);

        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingIn))
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(BookingStatus.WAITING.name())));
    }

    @Test
    void shouldGet() throws Exception {
        when(service.getById(anyLong(), anyLong())).thenReturn(bookingResponseDto);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(BookingStatus.WAITING.name())));
    }

    @Test
    void shouldApprove() throws Exception {
        bookingResponseDto.setStatus(BookingStatus.APPROVED);

        when(service.approveBooking(anyBoolean(), anyLong(), anyLong()))
                .thenReturn(bookingResponseDto);

        mvc.perform(patch("/bookings/1")
                        .param("approved", "true")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(BookingStatus.APPROVED.name())));

        bookingResponseDto.setStatus(BookingStatus.REJECTED);

        mvc.perform(patch("/bookings/1")
                        .param("approved", "false")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(BookingStatus.REJECTED.name())));

        mvc.perform(patch("/bookings/1")
                        .param("approved", "error")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetByBooker() throws Exception {
        when(service.getUserBookingsByState(anyLong(), anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingResponseDto));

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", "1")
                        .param("state", "notExistsParam")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetByOwner() throws Exception {
        when(service.getBookingsByItemsOwner(anyLong(), anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingResponseDto));

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}