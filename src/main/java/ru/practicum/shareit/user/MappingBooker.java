package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.Booker;

public class MappingBooker {

    public static Booker mapToBooker(Long id) {
        return Booker.builder()
                .id(id)
                .build();
    }
}
