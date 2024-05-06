package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDto getById(long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException("Бронирование не найдено."));

        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new DataNotFoundException("Бронирование не найдено.");
        }

        return BookingMapper.toBookingResponseDto(booking);
    }

    @Override
    @Transactional
    public BookingResponseDto create(BookingCreateDto bookingDto, long userId) {
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new DataNotFoundException("Объект для бронирования не найден."));

        if (!item.getAvailable()) {
            throw new NotAvailableItemCanNotBeBookException("Недоступно для бронирования.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Пользователь не найден."));

        if (user.getId().equals(item.getOwner().getId())) {
            throw new OwnerCanNotBeBookerException("Владелец не может забронировать свой предмет.");
        }

        return BookingMapper.toBookingResponseDto(bookingRepository.save(BookingMapper.toNewBooking(bookingDto, item, user)));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookingResponseDto approveBooking(boolean isApproved, long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException("Бронирование не найдено"));

        if (userId != booking.getItem().getOwner().getId()) {
            throw new DataNotFoundException("Только владелец может менять статус.");
        }

        if ((booking.getStatus().equals(BookingStatus.APPROVED) && isApproved)
                || (booking.getStatus().equals(BookingStatus.REJECTED) && !isApproved)) {
            throw new WrongStateException("Указанный статус уже выставлен.");
        }

        if (isApproved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        return BookingMapper.toBookingResponseDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDto> getUserBookingsByState(long userId, String state) {
        List<Booking> allBookings = bookingRepository.findAllByBookerId(userId);

        if (allBookings.isEmpty()) {
            return new ArrayList<>();
        }

        return sortByDateAndFilterBookingsByState(allBookings, state);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDto> getBookingsByItemsOwner(long ownerId, String state) {
        List<Booking> allBookings = bookingRepository.findAllByItemOwnerIdOrderById(ownerId);

        if (allBookings.isEmpty()) {
            return new ArrayList<>();
        }

        return sortByDateAndFilterBookingsByState(allBookings, state);
    }

    private List<BookingResponseDto> sortByDateAndFilterBookingsByState(List<Booking> bookingList, String state) {
        bookingList = bookingList.stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .collect(Collectors.toList());

        switch (state) {
            case ("WAITING"):
                bookingList = bookingList.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.WAITING))
                        .collect(Collectors.toList());
                break;
            case ("REJECTED"):
                bookingList = bookingList.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.REJECTED))
                        .collect(Collectors.toList());
                break;
            case ("CURRENT"):
                bookingList = bookingList.stream()
                        .filter(booking -> booking.getStart().isBefore(LocalDateTime.now())
                                && booking.getEnd().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
                break;
            case ("PAST"):
                bookingList = bookingList.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED)
                                && booking.getEnd().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
                break;
            case ("FUTURE"):
                bookingList = bookingList.stream()
                        .filter(booking -> !booking.getStatus().equals(BookingStatus.REJECTED)
                                && booking.getStart().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
                break;
        }

        return BookingMapper.mapBookingResponseDto(bookingList);
    }
}
