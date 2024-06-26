package ru.practicum.shareit.validation.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.data.ItemStorage;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.validation.exeption.NotFoundException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidationItem {

    private final ItemStorage itemStorage;

    public void validationItemById(Long id) {
        if (itemStorage.getItemById(id) == null) {
            log.info("Элемент с ID {} не найден", id);
            throw new NotFoundException("Пользователь с ID " + id + " не зарегистрирован");
        }
    }

    public void validationItemByUser(Long itemId, Long userId) {
        Item item = itemStorage.getItemById(itemId);
        if (!item.getOwnerId().equals(userId)) {
            log.info("{} не принадлежит пользователю с ID {}", item.getName(), userId);
            throw new NotFoundException(item.getName() + "не принадлежит пользователю с ID " + userId);
        }
    }
}
