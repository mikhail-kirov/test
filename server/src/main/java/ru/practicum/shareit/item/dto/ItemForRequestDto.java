package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class ItemForRequestDto {
    private Long id;
    private String name;
    private Long ownerId;
}
