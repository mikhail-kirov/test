package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

public class MappingBooking {

    public static BookingDto mapToBookingDto(Booking booking) {
        return BookingDto.builder()
                .ownerUser(null)
                .tenantUser(null)
                .items(null)
                .build();
    }

    public static Booking mapToBooking(BookingDto bookingDto) {
        return Booking.builder()
                .itemId(null)
                .tenantId(bookingDto.getTenantUser().getId())
                .bookingDate(null)
                .build();
    }
}
