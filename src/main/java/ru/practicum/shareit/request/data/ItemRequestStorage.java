package ru.practicum.shareit.request.data;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.model.ItemRequest;

@Component
public class ItemRequestStorage {

    private final ItemRequestData itemRequestData = new ItemRequestData();
    private long id = 0;

    public ItemRequest addItemRequest(ItemRequest itemRequest) {
        itemRequest.setRequestId(++id);
        itemRequestData.setItemRequest(itemRequest);
        return getItemRequestById(id);
    }

    public ItemRequest getItemRequestById(Long id) {
        return itemRequestData.getItemRequestById(id);
    }
}
