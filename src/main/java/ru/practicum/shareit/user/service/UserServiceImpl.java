package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.data.UserStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.validation.user.ValidationUser;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final ValidationUser validationUser;

    @Override
    public User addUser(User user) {
        validationUser.validationDuplicateUser(user);
        User user1 = userStorage.addUser(user);
        log.info("Пользователь {} добавлен", user.getEmail());
        return user1;
    }

    @Override
    public User updateUser(Long userId, User user) {
        validationUser.validationUserById(userId);
        user.setId(userId);
        validationUser.validationUpdateUser(user);
        User us = userStorage.getUserById(userId);
        if (user.getEmail() != null) {
            us.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            us.setName(user.getName());
        }
        log.info("Данные пользователя {} обновлены", us.getEmail());
        return userStorage.updateUser(us);
    }

    @Override
    public User removeUser(Long id) {
        validationUser.validationUserById(id);
        User user = getUserById(id);
        userStorage.removeUser(id);
        log.info("Пользователь {} удален", user.getEmail());
        return user;
    }

    @Override
    public User getUserById(Long id) {
        validationUser.validationUserById(id);
        User user = userStorage.getUserById(id);
        log.info("Найден пользователь с ID {} найден: {}", id, user.getEmail());
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        Collection<User> users = userStorage.getUsers();
        log.info("Список пользователей сформирован и отправлен");
        return users;
    }
}
