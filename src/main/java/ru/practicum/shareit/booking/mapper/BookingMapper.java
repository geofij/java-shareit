package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingInItemDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemInBookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserInBookingDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {
    public static Booking toNewBooking(BookingCreateDto bookingDto, Item item, User booker) {
        return Booking.builder()
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .item(item)
                .booker(booker)
                .status(BookingStatus.WAITING)
                .build();
    }

    public static BookingInItemDto toBookingInItemDto(Booking booking) {
        if (booking.getId() == null && booking.getBooker() == null) {
            return null;
        }

        return BookingInItemDto.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .build();
    }

    public static BookingResponseDto toBookingResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(ItemInBookingDto.builder()
                        .id(booking.getItem().getId())
                        .name(booking.getItem().getName())
                        .build())
                .booker(UserInBookingDto.builder()
                        .id(booking.getBooker().getId())
                        .build())
                .status(booking.getStatus())
                .build();
    }

    public static List<BookingResponseDto> mapBookingResponseDto(List<Booking> bookings) {
        List<BookingResponseDto> dtos = new ArrayList<>();

        for (Booking booking : bookings) {
            dtos.add(toBookingResponseDto(booking));
        }

        return dtos;
    }
}
