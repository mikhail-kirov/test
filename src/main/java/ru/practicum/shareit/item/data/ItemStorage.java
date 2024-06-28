package ru.practicum.shareit.item.data;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemStorage {

    Item addItem(ItemDto itemDto, Long userId);

    Item getItemById(Long id);

    Item updateItem(Item item);

    Collection<Item> getAllItem();

    Collection<Item> getItemsByUserId(Long id);
}
