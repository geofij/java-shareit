package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserUpdateDto {
    private String name;

    @Email
    private String email;
}
