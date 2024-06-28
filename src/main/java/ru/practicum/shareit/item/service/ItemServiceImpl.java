package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.MappingItem;
import ru.practicum.shareit.item.data.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.validation.item.ValidationItem;
import ru.practicum.shareit.validation.user.ValidationUser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemStorage itemStorage;
    private final ValidationUser validationUser;
    private final ValidationItem validationItem;

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        validationUser.validationUserById(userId);
        Item item = itemStorage.addItem(itemDto, userId);
        log.info("{} добавлен(а)", item.getName());
        return MappingItem.mapToItemDto(item);
    }

    @Override
    public ItemDto updateItemDto(Long userId, Long itemId, ItemDto itemDto) {
        validationUser.validationUserById(userId);
        validationItem.validationItemById(itemId);
        validationItem.validationItemByUser(itemId, userId);
        Item item = itemStorage.getItemById(itemId);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        log.info("Данные {} обновлены", itemDto.getName());
        return MappingItem.mapToItemDto(itemStorage.updateItem(item));
    }

    @Override
    public ItemDto getItemDtoById(Long userId, Long itemId) {
        validationUser.validationUserById(userId);
        validationItem.validationItemById(itemId);
        Item item = itemStorage.getItemById(itemId);
        log.info("По ID {} найден(а) {}", itemId, item.getName());
        return MappingItem.mapToItemDto(item);
    }

    @Override
    public Collection<ItemDto> getItemsDtoByUserId(Long userId) {
        validationUser.validationUserById(userId);
        Collection<Item> items = itemStorage.getItemsByUserId(userId);
        log.info("Список всех вещей пользователя получен и отправлен");
        return items.stream()
                .map(MappingItem::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> getItemsDtoBySearch(Long userId, String search) {
        validationUser.validationUserById(userId);
        Collection<Item> items = itemStorage.getAllItem();
        log.info("Список для поиска получен");
        if (!search.trim().isEmpty()) {
            return items.stream()
                    .filter(item -> item.getName().toLowerCase().contains(search)
                            || item.getDescription().toLowerCase().contains(search)
                            && item.getAvailable().equals(true))
                    .map(MappingItem::mapToItemDto)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
