package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

@Data
@Builder
public class ItemRequestDto {
    private User sourceRequestUser;
    private String request;
    private List<Item> resultItems;
}
