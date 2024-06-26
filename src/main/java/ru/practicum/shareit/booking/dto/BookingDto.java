package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.Set;

@Data
@Builder
public class BookingDto {
    private User ownerUser;
    private User tenantUser;
    private Set<Item> items;
}
