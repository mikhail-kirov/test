package ru.practicum.shareit.booking.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.MappingItem;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest(properties = "jdbc.url=jdbc:h2:mem:shareit://localhost:8070/test",
                webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BookingServiceBdTest {

    private final BookingService bookingService;
    private final EntityManager em;

    @BeforeEach
    void setUp() {
        User user = new User(null,"Mike", "test@mail.com");
        em.persist(user);

        ItemDto item = ItemDto.builder()
                .name("dsfg")
                .description("sdfsd")
                .available(true)
                .build();
        em.persist(MappingItem.mapToItem(item, 2L));
        em.flush();
    }

    @Test
    void saveBookingTest() {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(3);

        BookingRequestDto bookingDto = new BookingRequestDto(1L,start,end, BookingStatus.WAITING);

        bookingService.addBooking(1L, bookingDto);

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b WHERE b.id = :id", Booking.class);
        Booking booking = query.setParameter("id", 1).getSingleResult();

        assertThat(booking.getId(), notNullValue());
        assertEquals(booking.getBookerId(), 1, "ID арендатора не сохранилось в БД");
        assertThat(booking.getItem().getId(), equalTo(1L));
        assertThat(booking.getStartTime(), equalTo(start));
        assertThat(booking.getEndTime(), equalTo(end));
        assertThat(booking.getStatus(), equalTo(0));
    }
}
