package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.DataNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.storage.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public RequestResponseDto create(RequestCreateDto request, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Пользователь не найден."));

        ItemRequest newRequest = RequestMapper.toItemRequest(request, user);

        return RequestMapper.toItemRequestDto(requestRepository.save(newRequest), new ArrayList<>(), LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestResponseDto> getAllUserRequests(long userId) {
        List<ItemRequest> allUserRequests = requestRepository.findAllByRequesterId(userId);

        return getRequestDtoListFromRequestList(allUserRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestResponseDto> getAllRequests(int from, int size, long userId) {
        Pageable reqPage = PageRequest.of(from / size, size, Sort.by("id").descending());

        List<ItemRequest> requests = requestRepository.findAllByRequesterIdNot(userId, reqPage);

        return getRequestDtoListFromRequestList(requests);
    }

    @Override
    @Transactional(readOnly = true)
    public RequestResponseDto getRequestById(long requestId) {
        ItemRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException("Запрос вещи не найден."));

        List<Item> items = itemRepository.findAllByRequestIdInOrderByIdDesc(List.of(requestId));

        return RequestMapper.toItemRequestDto(request, items, LocalDateTime.now().minusSeconds(10));
    }

    private List<RequestResponseDto> getRequestDtoListFromRequestList(List<ItemRequest> requestList) {
        List<RequestResponseDto> dtos = new ArrayList<>();

        List<Long> requestIds = requestList.stream()
                .map(ItemRequest::getId)
                .collect(Collectors.toList());

        List<Item> items = itemRepository.findAllByRequestIdInOrderByIdDesc(requestIds);

        for (ItemRequest request : requestList) {
            List<Item> requestItems = items.stream()
                    .filter(item -> item.getRequest().getId().equals(request.getId()))
                    .collect(Collectors.toList());

            dtos.add(RequestMapper.toItemRequestDto(request, requestItems, LocalDateTime.now().minusSeconds(10)));
        }

        return dtos;
    }
}
