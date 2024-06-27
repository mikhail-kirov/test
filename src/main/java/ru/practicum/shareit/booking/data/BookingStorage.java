package ru.practicum.shareit.booking.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;

@Component
@RequiredArgsConstructor
public class BookingStorage {

    private final InMemoryBookingStorage bookingData;

    public Booking addBooking(Booking booking) {
        bookingData.setBooking(booking);
        return booking;
    }

    public Booking getBookingByItemId(Long id) {
        return bookingData.getBookingByItemId(id);
    }
}
