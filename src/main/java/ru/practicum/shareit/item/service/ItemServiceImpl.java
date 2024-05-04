package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.DataNotFoundException;
import ru.practicum.shareit.exception.ItemNotBeBookedException;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentCreatedResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseWithBookingAndCommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemResponseWithBookingAndCommentDto getById(Long id, Long userId) {
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
    @Transactional
    public ItemResponseDto save(Item item) {
        return ItemMapper.toItemInfoDto(itemRepository.save(item));
    }


    @Override
    @Transactional
    public ItemResponseDto update(Item item, Item itemUpdate) {
        return ItemMapper.toItemInfoDto(itemRepository.save(updateItemFromDto(item, itemUpdate)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<ItemResponseWithBookingAndCommentDto> getAllOwnerItems(long ownerId) {
        List<Item> itemsFromDb = itemRepository.findAllByOwnerIdOrderById(ownerId);
        List<ItemResponseWithBookingAndCommentDto> dtos = new ArrayList<>();

        List<Long> itemIds = itemsFromDb.stream()
                .map(Item::getId)
                .collect(Collectors.toList());

        List<Booking> allBookings = bookingRepository.findAllByItemIdInAndStatusNotOrderByStart(itemIds,
                BookingStatus.REJECTED);
        List<Comment> allComments = commentRepository.findAllByItemIdIn(itemIds);

        for (Item item : itemsFromDb) {
            dtos.add(getItemWithBookingsAndComments(item, allBookings, allComments));
        }

        return dtos.stream()
                .sorted(Comparator.comparing(ItemResponseWithBookingAndCommentDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemResponseDto> searchByText(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return new ArrayList<>();
        }

        return ItemMapper.mapItemInfoDto(itemRepository.searchByText(text).stream()
                .filter(Item::getAvailable)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
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

    private ItemResponseWithBookingAndCommentDto getItemWithBookingsAndComments(Item item, long userId) {
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
            lastBooking = lastBookings.get(0);
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

    private ItemResponseWithBookingAndCommentDto getItemWithBookingsAndComments(Item item, List<Booking> bookings, List<Comment> comments) {
        log.info("BOOKINGS" + bookings);
        log.info("COMMENTS" + comments);

        List<CommentCreatedResponseDto> commentsDto = comments.stream()
                .filter(comment -> comment.getItem().getId().equals(item.getId()))
                .map(CommentMapper::toCommentCreatedResponseDto)
                .collect(Collectors.toList());

        List<Booking> itemBookings = bookings.stream()
                .filter(booking -> booking.getItem().getId().equals(item.getId()))
                .collect(Collectors.toList());

        LocalDateTime now = LocalDateTime.now();
        Optional<Booking> lastBookingOpt;
        Optional<Booking> nextBookingOpt;
        Booking lastBooking;
        Booking nextBooking;

        lastBookingOpt = itemBookings.stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .filter(booking -> booking.getStart().isBefore(now) || booking.getEnd().isBefore(now))
                .findFirst();

        lastBooking = lastBookingOpt.orElseGet(Booking::new);

        if (lastBooking.getEnd() != null) {
            nextBookingOpt = itemBookings.stream()
                    .sorted(Comparator.comparing(Booking::getStart))
                    .filter(booking -> booking.getStart().isAfter(lastBooking.getEnd()))
                    .findFirst();
        } else {
            nextBookingOpt = itemBookings.stream()
                    .sorted(Comparator.comparing(Booking::getStart))
                    .filter(booking -> booking.getStart().isAfter(now))
                    .findFirst();
        }

        nextBooking = nextBookingOpt.orElseGet(Booking::new);

        return ItemMapper.toItemResponseDto(item,
                BookingMapper.toBookingInItemDto(lastBooking),
                BookingMapper.toBookingInItemDto(nextBooking),
                commentsDto);
    }
}
