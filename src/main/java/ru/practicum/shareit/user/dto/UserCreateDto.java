package ru.practicum.shareit.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@SuperBuilder
@NoArgsConstructor
public class UserCreateDto {
    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;
}
