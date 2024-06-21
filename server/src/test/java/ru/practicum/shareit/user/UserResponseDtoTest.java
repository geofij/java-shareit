package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.user.dto.UserResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserResponseDtoTest {
    @Autowired
    private JacksonTester<UserResponseDto> json;

    @Test
    void testSerialize() throws Exception {
        UserResponseDto dto = UserResponseDto.builder()
                .id(1L)
                .name("user")
                .email("user@user.ru")
                .build();

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.email");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo(dto.getEmail());
    }
}