package ru.practicum.shareit.item.data;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.MappingItem;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@Component
public class ItemStorage {

    private final ItemData itemData = new ItemData();
    private long id = 0;

    public Item addItem(ItemDto itemDto, Long userId) {
        itemData.setItem(MappingItem.mapToItem(itemDto, ++id, userId));
        return getItemById(id);
    }

    public Item getItemById(Long id) {
        return itemData.getItemById(id);
    }

    public Item updateItem(Item item) {
        itemData.setItem(item);
        return getItemById(item.getId());
    }

    public Collection<Item> getAllItem() {
        return itemData.getItems().values();
    }

    public Collection<Item> getItemsByUserId(Long id) {
        return itemData.getItemsByUserId(id);
    }
}
