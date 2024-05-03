package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.BookingStartEndValidationException;
import ru.practicum.shareit.exception.WrongStateException;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;

    @PostMapping
    public Booking createBooking(@Valid @RequestBody BookingCreateDto bookingDto,
                                 @RequestHeader("X-Sharer-User-Id") long userId) {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().equals(bookingDto.getEnd())) {
            throw new BookingStartEndValidationException("Начало бронирования не может быть позже или равно окончанию.");
        }

        return bookingService.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public Booking approveBookingByOwner(@RequestParam("approved") boolean isApproved,
                                         @PathVariable("bookingId") long bookingId,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.approveBooking(isApproved, bookingId, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable("bookingId") long bookingId,
                                  @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getById(bookingId, userId);
    }

    @GetMapping
    public List<Booking> getCurrentUserBookingsByState(@RequestParam(name = "state", defaultValue = "ALL") String state,
                                                       @RequestHeader("X-Sharer-User-Id") long userId) {
        validateState(state);
        userService.getById(userId);

        return bookingService.getUserBookingsByState(userId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getBookingsByItemsOwner(@RequestParam(name = "state", defaultValue = "ALL") String state,
                                                 @RequestHeader("X-Sharer-User-Id") long userId) {
        validateState(state);
        userService.getById(userId);

        return bookingService.getBookingsByItemsOwner(userId, state);
    }

    private void validateState(String state) {
        if (!state.equals("ALL") &&
                !state.equals("WAITING") &&
                !state.equals("REJECTED") &&
                !state.equals("CURRENT") &&
                !state.equals("PAST") &&
                !state.equals("FUTURE")) {
            throw new WrongStateException("Unknown state: UNSUPPORTED_STATUS");
        }
    }
}
