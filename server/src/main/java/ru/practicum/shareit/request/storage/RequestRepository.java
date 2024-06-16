package ru.practicum.shareit.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequesterId(Long id);

    List<ItemRequest> findAllByRequesterIdNot(Long id, Pageable page);
}
