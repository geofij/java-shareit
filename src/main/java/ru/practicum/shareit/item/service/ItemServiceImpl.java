package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.DataNotFoundException;
import ru.practicum.shareit.exception.ItemNotBeBookedException;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentCreatedResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemResponseDto getById(Long id, Long userId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Вещь не найдена."));

        return getItemWithBookingsAndComments(item, userId);
    }
    @Override
    public Item getById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Вещь не найдена."));
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }


    @Override
    public Item update(Item item, Item itemUpdate) {
        return itemRepository.save(updateItemFromDto(item, itemUpdate));
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<ItemResponseDto> getAll(long ownerId) {
        List<Item> itemsFromDb = itemRepository.findAllByOwnerIdOrderById(ownerId);
        List<ItemResponseDto> dtos = new ArrayList<>();

        if (!itemsFromDb.isEmpty()) {
            for (Item item : itemsFromDb) {
                dtos.add(getItemWithBookingsAndComments(item, ownerId));
            }
        }

        return dtos;
    }

    @Override
    public List<Item> searchByText(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return new ArrayList<>();
        }

        return itemRepository.searchByText(text).stream()
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public CommentCreatedResponseDto createNewComment(CommentCreateDto commentDto, long itemId, long userId) {
        List<Booking> bookingList = bookingRepository.findAllByItemIdAndBookerIdAndStatusAndEndBefore(itemId,
                                                                                            userId,
                                                                                            BookingStatus.APPROVED,
                                                                                            LocalDateTime.now());

        if (bookingList.isEmpty()) {
            throw new ItemNotBeBookedException("Вы не бронировали ранее эту вещь.");
        }

        Booking booking = bookingList.get(0);

        Comment createdComment = commentRepository.save(CommentMapper.toComment(commentDto,
                                                                                booking.getBooker(),
                                                                                booking.getItem()));

        return CommentMapper.toCommentCreatedResponseDto(createdComment);
    }

    private Item updateItemFromDto(Item itemDto, Item itemUpdate) {
        if (itemDto.getName() != null) {
            itemUpdate.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            itemUpdate.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            itemUpdate.setAvailable(itemDto.getAvailable());
        }

        return itemUpdate;
    }

    private ItemResponseDto getItemWithBookingsAndComments(Item item, long userId) {
        List<Comment> commentsFromDb = commentRepository.findAllByItemId(item.getId());
        List<CommentCreatedResponseDto> commentDtos = new ArrayList<>();

        for (Comment comment : commentsFromDb) {
            commentDtos.add(CommentMapper.toCommentCreatedResponseDto(comment));
        }

        if (item.getOwner().getId() != userId) {
            return ItemMapper.toItemResponseDto(item, commentDtos);
        }

        List<Booking> lastBookings = bookingRepository.findLastBooking(item.getId(),
                BookingStatus.APPROVED,
                LocalDateTime.now());

        Booking lastBooking = new Booking();

        if (!lastBookings.isEmpty()) {
            lastBooking = lastBookings.get(lastBookings.size() - 1);
        }

        List<Booking> nextBookings = bookingRepository.findAllByItemIdAndStatusNotAndStartAfterOrderByStart(item.getId(),
                BookingStatus.REJECTED,
                lastBooking.getEnd());

        Booking nextBooking = new Booking();

        if (!nextBookings.isEmpty()) {
            nextBooking = nextBookings.get(0);
        }

        return ItemMapper.toItemResponseDto(item,
                BookingMapper.toBookingInItemDto(lastBooking),
                BookingMapper.toBookingInItemDto(nextBooking),
                commentDtos);
    }
}
