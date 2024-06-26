package ru.practicum.shareit.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRequest {
    private Long requestId;
    private Long userId;
    private String request;
}
