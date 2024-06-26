package ru.practicum.shareit.user.data;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final UserData userData = new UserData();
    private long id = 0;

    @Override
    public User addUser(User user) {
        user.setId(++id);
        userData.setUser(user);
        return getUserById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userData.getUserById(id);
    }

    @Override
    public User updateUser(User user) {
        userData.setUser(user);
        return getUserById(user.getId());
    }

    @Override
    public void removeUser(Long id) {
        userData.removeUser(id);
    }

    @Override
    public Collection<User> getUsers() {
        return userData.getUsers().values();
    }
}
