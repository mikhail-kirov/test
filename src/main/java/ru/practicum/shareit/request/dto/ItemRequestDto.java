package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Data
@Builder
public class ItemRequestDto {
    private User sourceRequestUser;
    private List<Item> resultItems;
}
