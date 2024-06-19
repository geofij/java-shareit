package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;

    @PostMapping
    public BookingResponseDto createBooking(@RequestBody BookingCreateDto bookingDto,
                                            @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto approveBookingByOwner(@RequestParam("approved") boolean isApproved,
                                         @PathVariable("bookingId") long bookingId,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.approveBooking(isApproved, bookingId, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto getBookingById(@PathVariable("bookingId") long bookingId,
                                  @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getById(bookingId, userId);
    }

    @GetMapping
    public List<BookingResponseDto> getCurrentUserBookingsByState(@RequestParam(name = "from", defaultValue = "0") int from,
                                                                  @RequestParam(name = "size", defaultValue = "20") int size,
                                                                  @RequestParam(name = "state", defaultValue = "ALL") String state,
                                                       @RequestHeader("X-Sharer-User-Id") long userId) {
        userService.isUserExist(userId);

        return bookingService.getUserBookingsByState(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingResponseDto> getBookingsByItemsOwner(@RequestParam(name = "from", defaultValue = "0") int from,
                                                            @RequestParam(name = "size", defaultValue = "20") int size,
                                                            @RequestParam(name = "state", defaultValue = "ALL") String state,
                                                 @RequestHeader("X-Sharer-User-Id") long userId) {
        userService.isUserExist(userId);

        return bookingService.getBookingsByItemsOwner(userId, state, from, size);
    }
}
