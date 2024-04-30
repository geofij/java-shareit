package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@SuperBuilder
@NoArgsConstructor
public class BookingDto {

    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    @NotNull
    private Long bookerId;

    @NotNull
    private BookingStatus status;
}
