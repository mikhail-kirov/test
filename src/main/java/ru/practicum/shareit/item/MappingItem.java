package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class MappingItem {

    public static ItemDto mapToItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static Item mapToItem(ItemDto itemDto, Long id, Long userId) {
        return Item.builder()
                .id(id)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .ownerId(userId)
                .available(itemDto.getAvailable())
                .build();
    }
}
