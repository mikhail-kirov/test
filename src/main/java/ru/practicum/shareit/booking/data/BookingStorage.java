package ru.practicum.shareit.booking.data;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;

@Component
public class BookingStorage {

    private final BookingData bookingData = new BookingData();

    public Booking addBooking(Booking booking) {
        bookingData.setBooking(booking);
        return booking;
    }

    public Booking getBookingByItemId(Long id) {
        return bookingData.getBookingByItemId(id);
    }
}
