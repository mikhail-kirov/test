package ru.practicum.shareit.item.integration;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.MappingItem;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;

@Transactional
@SpringBootTest(properties = "jdbc.url=jdbc:h2:mem:shareit://localhost:8070/test",
                webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemServiceTest {

    private final ItemService itemService;
    private final EntityManager em;
    private List<ItemDto> items;

    @BeforeEach
    void setUp() {
        User user1 = new User(null,"Mike", "test1@mail.com");
        em.persist(user1);

        items = List.of(
                createItemDto("dsfg", "sdfsd", true),
                createItemDto("dsfgse", "sdsdffsd", false));
        List<Item> items1 = items.stream()
                .map(itemDto -> MappingItem.mapToItem(itemDto, 1L))
                .toList();
        items1.forEach(em::persist);
        em.flush();
    }

    @Test
    void getItemsByUserIdTest() {

        Collection<ItemDto> itemsResult = itemService.getItemsDtoByUserId(1L);

        assertThat(itemsResult, hasSize(2));

        for (ItemDto itemDto : items) {
            assertThat(itemsResult, hasItem(allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("name", equalTo(itemDto.getName())),
                    hasProperty("description", equalTo(itemDto.getDescription())),
                    hasProperty("available", equalTo(itemDto.getAvailable())),
                    hasProperty("requestId", equalTo(null)),
                    hasProperty("lastBooking", equalTo(null)),
                    hasProperty("nextBooking", equalTo(null)),
                    hasProperty("comments", equalTo(List.of()))
            )));
        }
    }

    private ItemDto createItemDto(String name, String description, Boolean available) {
        return ItemDto.builder()
                .name(name)
                .description(description)
                .available(available)
                .build();
    }
}
