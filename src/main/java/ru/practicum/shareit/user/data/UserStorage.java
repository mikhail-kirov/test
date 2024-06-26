package ru.practicum.shareit.user.data;

import ru.practicum.shareit.user.User;

import java.util.Collection;

public interface UserStorage {

    User addUser(User user);

    User getUserById(Long id);

    User updateUser(User user);

    void removeUser(Long id);

    Collection<User> getUsers();
}
