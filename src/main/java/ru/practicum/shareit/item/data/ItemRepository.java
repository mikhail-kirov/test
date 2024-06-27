package ru.practicum.shareit.item.data;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Component
public class ItemRepository {

    private final Map<Long, Item> items = new HashMap<>();

    public void setItem(Item item) {
        items.put(item.getId(), item);
    }

    public Item getItemById(Long id) {
        return items.get(id);
    }

    public List<Item> getItemsByUserId(Long id) {
        return items.values().stream()
                .filter(item -> item.getOwnerId().equals(id))
                .collect(Collectors.toList());
    }
}
