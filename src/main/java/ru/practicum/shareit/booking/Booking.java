package ru.practicum.shareit.booking;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Booking {
    private Long itemId;
    private Long tenantId;
    private LocalDate bookingDate;
}


