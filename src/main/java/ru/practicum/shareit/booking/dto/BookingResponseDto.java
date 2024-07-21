package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.Booker;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class BookingResponseDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
    private Booker booker;
    private Item item;
}
