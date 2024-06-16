package ru.practicum.shareit.user.model;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "email", unique = true)
    private String email;
}
