package ru.practicum.shareit.item.unit;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.data.BookingRepository;
import ru.practicum.shareit.booking.data.CommentRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.data.ItemRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validation.exeption.BadRequestException;
import ru.practicum.shareit.validation.exeption.NotFoundException;
import ru.practicum.shareit.validation.item.ValidationItem;
import ru.practicum.shareit.validation.user.ValidationUser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceUnitTest {
    @Mock
    private ValidationUser validUser;
    @Mock
    private ValidationItem validItem;
    @Mock
    private ItemRepository itemRepo;
    @Mock
    private BookingRepository bookingRepo;
    @Mock
    private CommentRepository commentRepo;

    private ItemServiceImpl itemService;
    private Item item1;
    private Item item2;
    private ItemDto itemDto1;
    private Comment comment1;
    private List<Comment> comments;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private final LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    List<ItemDto> items;

    @BeforeEach
    void setUp() {
        itemService = new ItemServiceImpl(
                validUser,
                validItem,
                itemRepo,
                bookingRepo,
                commentRepo
        );
        item1 = new Item(1L, "Mik", "test1", 1L, true, 2L, List.of());
        item2 = new Item(2L, "Mark", "test2", 1L, false, 9L, List.of());
        itemDto1 = ItemDto.builder()
                .id(1L)
                .name("Mik")
                .description("test1")
                .available(true)
                .requestId(2L)
                .build();
        ItemDto itemDto2 = ItemDto.builder()
                .id(2L)
                .name("Mark")
                .description("test2")
                .available(false)
                .requestId(9L)
                .build();

        items = List.of(itemDto1, itemDto2);

        comment1 = new Comment(1L, "test1", "Mike", date, 1L);
        Comment comment2 = new Comment(5L, "test2", "Oleg", date.plusDays(2), 1L);

        comments = List.of(comment1, comment2);
    }

    @Test
    void createItemTest() {
        ItemDto itemTest = ItemDto.builder().name("Mik").description("test1").available(true).build();
        when(itemRepo.save(any())).thenReturn(item1);
        ItemDto itemDto = itemService.addItem(1L, itemTest);
        testItem(itemDto);
    }

    @Test
    void createCommentTest() {
        CommentDto commentTest = CommentDto.builder().text("test1").build();
        Booking booking1 = new Booking(1L, item1, 2L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 0);
        Booking booking2 = new Booking(2L, item1, 2L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 0);
        List<Booking> bookings = List.of(booking1, booking2);
        User user = new User(1L, "John", "test1@mail.com");
        Comment comment = new Comment(1L, "test1", "Mike", date, 1L);

        when(validUser.validationUserById(anyLong())).thenReturn(user);
        when(validItem.validationItemById(anyLong())).thenReturn(item1);
        when(bookingRepo.findAllBookingByBookerIdAndItemOrderByStartTimeDesc(anyLong(), any())).thenReturn(bookings);
        when(validItem.validationCommentByBookerId(any())).thenReturn(booking1);
        when(commentRepo.save(any())).thenReturn(comment);

        CommentDto commentDto = itemService.setCommentInItem(2L,1L, commentTest);
        assertThat(commentDto.getId(), equalTo(comment1.getId()));
        assertThat(commentDto.getText(), equalTo(comment1.getText()));
        assertThat(commentDto.getAuthorName(), equalTo(comment1.getAuthorName()));
        assertThat(commentDto.getCreated(), equalTo(comment1.getCreated()));
    }

    @Test
    void updateItemTest() {
        ItemDto itemTest = ItemDto.builder().name("Mark").description("test3").available(false).build();
        when(validItem.validationItemByUser(anyLong(), anyLong())).thenReturn(item1);

        item1.setName(itemTest.getName());
        item1.setDescription(itemTest.getDescription());
        item1.setAvailable(itemTest.getAvailable());

        when(itemRepo.save(any())).thenReturn(item1);

        ItemDto itemDto = itemService.updateItemDto(1L, 1L, itemTest);

        assertThat(itemDto.getId(), equalTo(1L));
        assertThat(itemDto.getName(), equalTo(itemTest.getName()));
        assertThat(itemDto.getDescription(), equalTo(itemTest.getDescription()));
        assertThat(itemDto.getAvailable(), equalTo(itemTest.getAvailable()));
    }

    @Test
    void getItemByIdTest() {
        item1.setComments(comments);
        when(validItem.validationItemById(anyLong())).thenReturn(item1);
        ItemDto itemDto = itemService.getItemDtoById(1L, 1L);
        testItem(itemDto);
        itemDto.getComments().forEach(
                com -> assertThat(comments, hasItem(allOf(
                        hasProperty("id", equalTo(com.getId())),
                        hasProperty("text", equalTo(com.getText())),
                        hasProperty("authorName", equalTo(com.getAuthorName())),
                        hasProperty("created", equalTo(com.getCreated()))
                )))
        );
    }

    @Test
    void getItemsDtoByUserIdTest() {
        List<Item> itemsTest = List.of(item1, item2);
        when(itemRepo.findByOwnerId(anyLong())).thenReturn(itemsTest);
        Collection<ItemDto> itemDto = itemService.getItemsDtoByUserId(1L);
        assertThat(itemDto, hasSize(items.size()));
        itemListTest(itemDto);
    }

    @Test
    void getItemsDtoBySearchTest() {
        List<Item> itemsTest = List.of(item1, item2);
        when(itemRepo.getItemBySearch(anyString())).thenReturn(itemsTest);
        Collection<ItemDto> itemDto = itemService.getItemsDtoBySearch(1L, "test");
        assertThat(itemDto, hasSize(items.size()));
        itemListTest(itemDto);
    }

    @Test
    void findItemNotFoundExceptionTest() {
        when(validItem.validationItemById(anyLong())).thenThrow(new NotFoundException("Вещь с ID 1 не найдена"));

        final NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> itemService.getItemDtoById(1L, anyLong())
        );
        Assertions.assertEquals("Вещь с ID 1 не найдена", exception.getMessage());
    }

    @Test
    void updateItemNotFoundExceptionTest() {
        when(validItem.validationItemByUser(anyLong(), anyLong()))
                .thenThrow(new NotFoundException("Вещь не принадлежит пользователю с ID 2"));

        final NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> itemService.updateItemDto(2L, 1L, any())
        );
        Assertions.assertEquals("Вещь не принадлежит пользователю с ID 2", exception.getMessage());
    }

    @Test
    void setCommentBadRequestExceptionTest() {
        when(validItem.validationCommentByBookerId(any()))
                .thenThrow(new BadRequestException("Пользоваетель не может оставить комментарий"));

        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> itemService.setCommentInItem(2L, 1L, any())
        );
        Assertions.assertEquals("Пользоваетель не может оставить комментарий", exception.getMessage());
    }

    private void itemListTest(Collection<ItemDto> itemDto) {
        itemDto.forEach(
                it -> assertThat(items, hasItem(allOf(
                        hasProperty("id", equalTo(it.getId())),
                        hasProperty("name", equalTo(it.getName())),
                        hasProperty("description", equalTo(it.getDescription())),
                        hasProperty("available", equalTo(it.getAvailable())),
                        hasProperty("requestId", equalTo(it.getRequestId()))
                )))
        );
    }

    private void testItem(ItemDto itemDto) {
        assertThat(itemDto.getId(), equalTo(itemDto1.getId()));
        assertThat(itemDto.getName(), equalTo(itemDto1.getName()));
        assertThat(itemDto.getDescription(), equalTo(itemDto1.getDescription()));
        assertThat(itemDto.getAvailable(), equalTo(itemDto1.getAvailable()));
        assertThat(itemDto.getRequestId(), equalTo(itemDto1.getRequestId()));
    }
}
