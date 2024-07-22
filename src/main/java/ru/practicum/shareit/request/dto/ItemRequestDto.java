package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;


@Data
@Builder
public class ItemRequestDto {
    private User sourceRequestUser;
}
