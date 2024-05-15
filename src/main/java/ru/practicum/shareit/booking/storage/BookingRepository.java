package ru.practicum.shareit.booking.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerId(Long bookerId, Pageable pageable);

    List<Booking> findAllByItemOwnerId(Long ownerId, Pageable pageable);

    List<Booking> findAllByItemIdAndBookerIdAndStatusAndEndBefore(Long itemId,
                                                                  Long bookerId,
                                                                  BookingStatus status,
                                                                  LocalDateTime date);

    @Query(" select b from Booking as b " +
            "where b.item.id = ?1 " +
            "and status = ?2 and (b.end < ?3 or b.start < ?3) " +
            "order by b.start desc")
    List<Booking> findLastBooking(Long itemId, BookingStatus status, LocalDateTime now);

    List<Booking> findAllByItemIdAndStatusNotAndStartAfterOrderByStart(Long itemId, BookingStatus status, LocalDateTime now);

    List<Booking> findAllByItemIdInAndStatusNotOrderByStart(List<Long> itemIds, BookingStatus status);
}
