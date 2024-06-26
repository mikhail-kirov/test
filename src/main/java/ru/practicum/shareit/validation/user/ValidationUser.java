package ru.practicum.shareit.validation.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.data.UserStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.validation.exeption.IncorrectParameterException;
import ru.practicum.shareit.validation.exeption.NotFoundException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidationUser {

    private final UserStorage userStorage;

    public void validationUserById(Long id) {
        if (userStorage.getUserById(id) == null) {
            log.info("Пользователь c ID {} не зарегистрирован", id);
            throw new NotFoundException("Пользователь с ID " + id + " не зарегистрирован");
        }
    }

    public void validationDuplicateUser(User user) {
        boolean valid = userStorage.getUsers().stream().anyMatch(us -> us.getEmail().equals(user.getEmail()));
        if (valid) {
            log.info("Пользователь с email {} уже зарегистрирован", user.getEmail());
            throw new IncorrectParameterException("Пользователь с email " + user.getEmail() + " уже зарегистрирован");
        }
    }

    public void validationUpdateUser(User user) {
        User user1 = userStorage.getUsers().stream()
                .filter(us -> us.getEmail().equals(user.getEmail()))
                .findFirst().orElse(null);
        if (user1 != null && !user1.getId().equals(user.getId())) {
            log.info("Ошибка обновления данных пользователя: неверный ID или email");
            throw new IncorrectParameterException("Ошибка обновления данных пользователя: неверный ID или email");
        }
    }
}
