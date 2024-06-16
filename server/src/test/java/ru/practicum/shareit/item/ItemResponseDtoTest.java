package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.ItemResponseDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class ItemResponseDtoTest {
    @Autowired
    private JacksonTester<ItemResponseDto> json;

    @Test
    void testSerialize() throws Exception {
        ItemResponseDto dto = ItemResponseDto.builder()
                .id(1L)
                .name("item")
                .description("itemDesc")
                .available(false)
                .build();

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.available");
        assertThat(result).hasJsonPath("$.requestId");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(dto.getAvailable());
        assertThat(result).doesNotHaveJsonPathValue("$.requestId");
    }
}