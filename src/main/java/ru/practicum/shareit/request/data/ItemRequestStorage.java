package ru.practicum.shareit.request.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.model.ItemRequest;

@Component
@RequiredArgsConstructor
public class ItemRequestStorage {

    private final InMemoryItemRequestStorage itemRequestData;
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
