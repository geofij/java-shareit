package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CommentCreatedResponseDto {
    private Long id;
    private String text;
    private String authorName;
    private final LocalDateTime created = LocalDateTime.now();
}
