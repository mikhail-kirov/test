package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                            @Valid @RequestBody BookingRequestDto bookingRequestDto) {
        log.info("Запрос на добавление брони для вещь с ID {}", bookingRequestDto.getItemId());
        return bookingService.addBooking(userId, bookingRequestDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto updateBookingStatus(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @PathVariable Long bookingId,
                                                  @RequestParam (value = "approved") Boolean approved) {
        log.info("Запрос на обновление статуса брони с ID {}", bookingId);
        return bookingService.approvedBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto getBookingByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @PathVariable Long bookingId) {
        log.info("Запрос на получение данных о брони с ID {}", bookingId);
        return bookingService.findBookingByUserId(userId, bookingId);
    }

    @GetMapping
    public Collection<BookingResponseDto> getAllBookingsByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                               @RequestParam (value = "state", defaultValue = "ALL") String state) {
        log.info("Запрос на получение списка всех бронирований пользователя с ID {}", userId);
        return bookingService.getAllBookingsByUser(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingResponseDto> getAllBookingsByItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                                      @RequestParam (value = "state", defaultValue = "ALL") String state) {
        log.info("Запрос получение списка бронирований для всех вещей пользователя пользователя с ID {}", userId);
        return bookingService.getAllBookingsByItemsByUser(userId, state);
    }
}
