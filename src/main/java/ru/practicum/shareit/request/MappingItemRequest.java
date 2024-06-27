package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

public class MappingItemRequest {

    public static ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest) {
        return ItemRequestDto.builder()
                .sourceRequestUser(null)
                .request(itemRequest.getRequest())
                .build();
    }

    public static ItemRequest mapToItemRequest(ItemRequestDto itemRequestDto) {
        return ItemRequest.builder()
                .requestId(null)
                .userId(itemRequestDto.getSourceRequestUser().getId())
                .request(itemRequestDto.getRequest())
                .build();
    }
}
