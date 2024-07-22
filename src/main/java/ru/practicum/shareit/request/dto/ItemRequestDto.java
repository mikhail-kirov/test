package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Data
@Builder
public class ItemRequestDto {
    private List<Item> resultItems;
}
