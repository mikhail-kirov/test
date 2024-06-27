package ru.practicum.shareit.item.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.MappingItem;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class InMemoryItemStorage implements ItemStorage {

    private final ItemRepository itemData;
    private long id = 0;

    @Override
    public Item addItem(ItemDto itemDto, Long userId) {
        itemData.setItem(MappingItem.mapToItem(itemDto, ++id, userId));
        return getItemById(id);
    }

    @Override
    public Item getItemById(Long id) {
        return itemData.getItemById(id);
    }

    @Override
    public Item updateItem(Item item) {
        itemData.setItem(item);
        return getItemById(item.getId());
    }

    @Override
    public Collection<Item> getAllItem() {
        return itemData.getItems().values();
    }

    @Override
    public Collection<Item> getItemsByUserId(Long id) {
        return itemData.getItemsByUserId(id);
    }
}
