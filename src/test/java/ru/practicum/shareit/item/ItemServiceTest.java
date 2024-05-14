package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.DataNotFoundException;
import ru.practicum.shareit.exception.ItemNotBeBookedException;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemResponseWithBookingAndCommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.storage.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User user1;
    private User user2;
    private Item item;
    private Comment comment;
    private CommentCreateDto commentCreateDto;

    @BeforeEach
    void init() {
        user1 = User.builder()
                .id(1L)
                .email("user@user.user")
                .name("user")
                .build();

        user2 = User.builder()
                .id(2L)
                .email("user2@user.user")
                .name("user2")
                .build();

        item = Item.builder()
                .id(1L)
                .name("item")
                .description("item")
                .available(true)
                .owner(user1)
                .build();

        comment = Comment.builder()
                .id(1L)
                .text("comment")
                .item(item)
                .author(user1)
                .build();

        commentCreateDto = CommentCreateDto.builder()
                .text("comment")
                .build();
    }

    @Test
    void shouldSave() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        when(requestRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ItemRequest.builder()
                .id(1L)
                .build()));

        ItemCreateDto createDto = ItemCreateDto.builder()
                .name("item")
                .description("item")
                .available(true)
                .build();

        assert user1 != null;
        assertEquals(itemService.save(createDto, user1.getId()).getId(), 1L);

        createDto = ItemCreateDto.builder()
                .name("item")
                .description("item")
                .available(true)
                .requestId(1L)
                .build();

        assert user1 != null;
        assertEquals(itemService.save(createDto, user1.getId()).getId(), 1L);
    }

    @Test
    void shouldGet() {
        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(item));
        when(bookingRepository.findLastBooking(anyLong(), any(BookingStatus.class), any(LocalDateTime.class)))
                .thenReturn(List.of(Booking.builder()
                        .id(1L)
                        .item(item)
                        .start(LocalDateTime.parse("2024-05-01T00:00:00"))
                        .end(LocalDateTime.parse("2024-05-02T00:00:00"))
                        .status(BookingStatus.APPROVED)
                        .booker(user2)
                        .build()));

        when(bookingRepository.findAllByItemIdAndStatusNotAndStartAfterOrderByStart(anyLong(), any(BookingStatus.class), any(LocalDateTime.class)))
                .thenReturn(List.of(Booking.builder()
                        .id(2L)
                        .item(item)
                        .start(LocalDateTime.parse("2024-05-10T00:00:00"))
                        .end(LocalDateTime.parse("2024-05-11T00:00:00"))
                        .status(BookingStatus.WAITING)
                        .booker(user2)
                        .build()));


        assertEquals(itemService.getById(1L, 1L).getClass(), ItemResponseWithBookingAndCommentDto.class);
        assertEquals(itemService.getById(1L, 1L).getName(), "item");
        assertEquals(itemService.getById(1L, 1L).getNextBooking().getId(), 2L);

        assertNotNull(itemService.getById(1L));
        assertEquals(itemService.getById(1L).getClass(),
                Item.class);

        assertThrows(DataNotFoundException.class, () -> itemService.getById(2L, 1L));
    }

    @Test
    void shouldUpdate() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item));
        Item updItem = item;
        assert updItem != null;
        updItem.setDescription("newDescr");

        when(itemRepository.save(any(Item.class))).thenReturn(item);

        assertEquals(itemService.update(updItem, item).getDescription(), "newDescr");
    }

    @Test
    void shouldSearch() {
        when(itemRepository.searchByText(eq("item"), any(Pageable.class))).thenReturn(List.of(item));
        when(itemRepository.searchByText(eq("noItem"), any(Pageable.class))).thenReturn(List.of());

        assertEquals(itemService.searchByText("item", 1, 1).get(0).getId(), 1L);
        assertEquals(itemService.searchByText("noItem", 1, 1).size(), 0);
        assertEquals(itemService.searchByText("", 1, 1).size(), 0);
    }

    @Test
    void shouldComment() {
        when(bookingRepository.findAllByItemIdAndBookerIdAndStatusAndEndBefore(anyLong(), anyLong(), any(BookingStatus.class), any(LocalDateTime.class)))
                .thenReturn(List.of(Booking.builder()
                                .booker(user2)
                                .item(item)
                                .build()));

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        assertEquals(itemService.createNewComment(commentCreateDto, 1L, 2L).getText(), "comment");

        when(bookingRepository.findAllByItemIdAndBookerIdAndStatusAndEndBefore(anyLong(), anyLong(), any(BookingStatus.class), any(LocalDateTime.class)))
                .thenReturn(List.of());

        assertThrows(ItemNotBeBookedException.class, () -> itemService.createNewComment(commentCreateDto, 2L, 2L));
    }

    @Test
    void shouldGetAll() {
        when(itemRepository.findAllByOwnerId(anyLong(), any(Pageable.class))).thenReturn(List.of(item));

        assertEquals(itemService.getAllOwnerItems(1L, 1, 1).get(0).getDescription(), item.getDescription());
    }
}