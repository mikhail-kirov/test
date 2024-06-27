package ru.practicum.shareit.request.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRequest {
    private Long requestId;
    private Long userId;
    private String request;
}
