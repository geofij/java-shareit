package ru.practicum.shareit.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;

@Getter
@SuperBuilder
@NoArgsConstructor
public class UserUpdateDto {
    private String name;

    @Email
    private String email;
}
