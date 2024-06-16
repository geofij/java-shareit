package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@NoArgsConstructor
public class RequestCreateDto {
    @NotEmpty
    @NotBlank
    private String description;
}
