package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.dto.BookingInItemDto;

import java.util.List;

@Data
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
