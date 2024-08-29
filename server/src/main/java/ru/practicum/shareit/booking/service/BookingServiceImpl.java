package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.MappingBooking;
import ru.practicum.shareit.booking.data.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.data.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.data.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validation.booking.ValidationBooking;
import ru.practicum.shareit.validation.exeption.BadRequestException;
import ru.practicum.shareit.validation.exeption.NotFoundException;
import ru.practicum.shareit.validation.item.ValidationItem;
import ru.practicum.shareit.validation.user.ValidationUser;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {

    private final ValidationUser validationUser;
    private final ValidationItem validationItem;
    private final ValidationBooking validationBooking;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingResponseDto addBooking(Long userId, BookingRequestDto bookingRequestDto) {
        validationUser.validationUserById(userId);
        Item item = validationBooking.validationBookingDto(bookingRequestDto, userId);
        if (bookingRequestDto.getStatus() == null) {
            bookingRequestDto.setStatus(BookingStatus.WAITING);
        }
        Booking booking = bookingRepository.save(MappingBooking.mapToBooking(userId, bookingRequestDto, item));
        return MappingBooking.mapToBookingResponseDto(booking, item);
    }

    @Override
    public BookingResponseDto approvedBooking(Long userId, Long bookingId, Boolean approved) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("Пользователь с ID " + userId + " не зарегистрирован"));
        Booking booking = validationBooking.validationBookingById(bookingId);
        validationUser.validationUserById(booking.getBookerId());
        validationItem.validationItemByUser(booking.getItem().getId(), userId);
        BookingResponseDto bookingResponseDto = MappingBooking.mapToBookingResponseDto(booking);
        if (booking.getStatus().equals(1) && approved) {
            throw new BadRequestException("Бронирование уже подтверждено");
        }
        if (!booking.getItem().getOwnerId().equals(userId)) {
            throw new BadRequestException("Бронирование может подтверждать только собственник вещи");
        }
        if (approved) {
            bookingResponseDto.setStatus(BookingStatus.APPROVED);
            booking.setStatus(BookingStatus.APPROVED.ordinal());
        } else {
            bookingResponseDto.setStatus(BookingStatus.REJECTED);
            booking.setStatus(BookingStatus.REJECTED.ordinal());
        }
        bookingRepository.save(booking);
        return bookingResponseDto;
    }

    @Override
    public BookingResponseDto findBookingById(Long userId, Long bookingId) {
        Booking booking = validationBooking.validationBookingById(bookingId);
        validationUser.validationUserById(booking.getBookerId());
        validationItem.validationItemById(booking.getItem().getId());
        if (!booking.getBookerId().equals(userId)) {
            validationItem.validationItemByUser(booking.getItem().getId(), userId);
        }
        return MappingBooking.mapToBookingResponseDto(booking);
    }

    @Override
    public Collection<BookingResponseDto> getAllBookingsByUser(Long userId, String state) {
        validationUser.validationUserById(userId);
        List<Booking> bookings = bookingRepository.findAllBookingByBookerIdOrderByStartTimeDesc(userId);
        return getListBookingStateDtoFromBookings(bookings, state);
    }

    @Override
    public Collection<BookingResponseDto> getAllBookingsByItemsByUser(Long userId, String state) {
        validationUser.validationUserById(userId);
        List<Long> itemsId = itemRepository.findByOwnerId(userId).stream().map(Item::getId).toList();
        List<Booking> bookings = bookingRepository.findAllBookingByItemIdInOrderByStartTimeDesc(itemsId);
        return getListBookingStateDtoFromBookings(bookings, state);
    }

    private Collection<BookingResponseDto> getListBookingStateDtoFromBookings(List<Booking> bookings, String state) {
        Collection<Booking> bookingsSort = MappingBooking.mapToBookingState(bookings, state);
        return MappingBooking.mapToBookingResponseDto(bookingsSort);
    }
}
