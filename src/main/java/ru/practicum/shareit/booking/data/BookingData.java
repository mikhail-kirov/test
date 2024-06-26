package ru.practicum.shareit.booking.data;

import lombok.Getter;
import ru.practicum.shareit.booking.model.Booking;

import java.util.HashSet;
import java.util.Set;

@Getter
public class BookingData {

    private final Set<Booking> bookings = new HashSet<>();

    public void setBooking(Booking booking) {
        bookings.add(booking);
    }

    public Booking getBookingByItemId(Long id) {
        return bookings.stream().filter(b -> b.getItemId().equals(id)).findFirst().orElse(null);
    }
}
