package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.dto.BookingInItemDto;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ItemResponseWithBookingAndCommentDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
    private BookingInItemDto lastBooking;
    private BookingInItemDto nextBooking;
    private List<CommentCreatedResponseDto> comments;
}
