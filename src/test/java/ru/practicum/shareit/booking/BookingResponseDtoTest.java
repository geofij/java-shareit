package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemInBookingDto;
import ru.practicum.shareit.user.dto.UserInBookingDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class BookingResponseDtoTest {
    @Autowired
    private JacksonTester<BookingResponseDto> json;

    @Test
    void testSerialize() throws Exception {
        BookingResponseDto dto = BookingResponseDto.builder()
                .id(1L)
                .status(BookingStatus.APPROVED)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(10))
                .booker(UserInBookingDto.builder().build())
                .item(ItemInBookingDto.builder().build())
                .build();

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.status");
        assertThat(result).hasJsonPath("$.start");
        assertThat(result).hasJsonPath("$.end");
        assertThat(result).hasJsonPath("$.booker");
        assertThat(result).hasJsonPath("$.item");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(dto.getStatus().name());
        assertThat(result).hasJsonPathValue("$.start");
        assertThat(result).hasJsonPathValue("$.end");
        assertThat(result).hasJsonPathValue("$.booker");
        assertThat(result).hasJsonPathValue("$.item");
    }
}