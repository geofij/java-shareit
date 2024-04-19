package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
}
