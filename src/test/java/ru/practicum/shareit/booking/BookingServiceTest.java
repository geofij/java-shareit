package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User user;

    private User user2;

    private Item item;

    private Booking booking;

    private BookingCreateDto bookingCreateDto;

    @BeforeEach
    void init() {
        user = User.builder()
                .id(1L)
                .email("user@user.ru")
                .name("user")
                .build();

        user2 = User.builder()
                .id(2L)
                .email("user2@user.ru")
                .name("user2")
                .build();

        item = Item.builder()
                .id(1L)
                .name("item")
                .description("item")
                .available(true)
                .owner(user)
                .build();

        booking = Booking.builder()
                .id(1L)
                .item(item)
                .start(LocalDateTime.now().plusHours(10))
                .end(LocalDateTime.now().plusHours(100))
                .status(BookingStatus.APPROVED)
                .booker(user2)
                .build();

        bookingCreateDto = BookingCreateDto.builder()
                .start(LocalDateTime.now().plusHours(10))
                .end(LocalDateTime.now().plusHours(100))
                .itemId(1L)
                .build();
    }

    @Test
    void shouldSave() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user2));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        assertEquals(bookingService.create(bookingCreateDto, 2L).getBooker().getId(), 2L);
    }

    @Test
    void shouldApproved() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(booking));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        assertEquals(bookingService.approveBooking(false, 1L, 1L).getStatus(), BookingStatus.REJECTED);
    }

    @Test
    void shouldGet() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(booking));

        assertEquals(bookingService.getById(1L, 2L).getBooker().getId(), 2L);
    }

    @Test
    void shouldGetByBooker() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(bookingRepository.findAllByBookerId(anyLong(), any(Pageable.class))).thenReturn(List.of(booking));

        assertEquals(bookingService.getUserBookingsByState(2L, "ALL", 0, 1).get(0).getId(), 1L);
        assertEquals(bookingService.getUserBookingsByState(2L, "FUTURE", 0, 1).get(0).getId(), 1L);

        booking.setStatus(BookingStatus.REJECTED);
        assertEquals(bookingService.getUserBookingsByState(2L, "REJECTED", 0, 1).get(0).getId(), 1L);

        booking.setStatus(BookingStatus.WAITING);
        assertEquals(bookingService.getUserBookingsByState(2L, "WAITING", 0, 1).get(0).getId(), 1L);

        booking.setStatus(BookingStatus.APPROVED);
        booking.setStart(LocalDateTime.now().minusHours(50));
        booking.setEnd(LocalDateTime.now().minusHours(30));
        assertEquals(bookingService.getUserBookingsByState(2L, "PAST", 0, 1).get(0).getId(), 1L);

        booking.setEnd(booking.getEnd().plusDays(5));
        assertEquals(bookingService.getUserBookingsByState(2L, "CURRENT", 0, 1).get(0).getId(), 1L);
    }

    @Test
    void shouldGetByOwner() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(bookingRepository.findAllByItemOwnerId(anyLong(), any(Pageable.class))).thenReturn(List.of(booking));

        assertEquals(bookingService.getBookingsByItemsOwner(1L, "ALL", 0, 1).get(0).getId(), 1L);
        assertEquals(bookingService.getBookingsByItemsOwner(1L, "FUTURE", 0, 1).get(0).getId(), 1L);

        booking.setStatus(BookingStatus.REJECTED);
        assertEquals(bookingService.getBookingsByItemsOwner(1L, "REJECTED", 0, 1).get(0).getId(), 1L);

        booking.setStatus(BookingStatus.WAITING);
        assertEquals(bookingService.getBookingsByItemsOwner(1L, "WAITING", 0, 1).get(0).getId(), 1L);

        booking.setStatus(BookingStatus.APPROVED);
        booking.setStart(LocalDateTime.now().minusHours(50));
        booking.setEnd(LocalDateTime.now().minusHours(30));
        assertEquals(bookingService.getBookingsByItemsOwner(1L, "PAST", 0, 1).get(0).getId(), 1L);

        booking.setEnd(booking.getEnd().plusDays(5));
        assertEquals(bookingService.getBookingsByItemsOwner(1L, "CURRENT", 0, 1).get(0).getId(), 1L);
    }
}