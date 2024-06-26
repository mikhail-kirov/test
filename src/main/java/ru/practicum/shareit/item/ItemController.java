package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @Valid @RequestBody ItemDto itemDto) {
        log.info("Запрос на добавление {}", itemDto.getName());
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @PathVariable Long itemId,
                              @RequestBody ItemDto itemDto) {
        log.info("Запрос на обновление {} у пользователя с ID {}", itemDto.getName(), userId);
        return itemService.updateItemDto(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @PathVariable Long itemId) {
        log.info("Запрос на получение данных вещи с ID {} у пользователя с ID {}", itemId, userId);
        return itemService.getItemDtoById(userId, itemId);
    }

    @GetMapping()
    public Collection<ItemDto> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на получение списка данных по всем вещам пользователя с ID {}", userId);
        return itemService.getItemsDtoByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> getItemsBySearch(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @RequestParam(value = "text") String text) {
        log.info("Запрос на поиск вещей пользователя с ID {} по параметру '{}'", userId, text);
        return itemService.getItemsDtoBySearch(userId, text.toLowerCase());
    }
}
