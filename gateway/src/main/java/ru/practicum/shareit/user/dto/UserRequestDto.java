package ru.practicum.shareit.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserRequestDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}
