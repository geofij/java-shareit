package ru.practicum.shareit.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
public class BookingCreateDto {

    @NotNull
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull
    @Future
    private LocalDateTime end;

    @NotNull
    private Long itemId;
}
