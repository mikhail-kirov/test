package ru.practicum.shareit.user.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {

    private final UserRepository userRepository;
    private long id = 0;

    @Override
    public User addUser(User user) {
        user.setId(++id);
        userRepository.setUser(user);
        return getUserById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User updateUser(User user) {
        userRepository.setUser(user);
        return getUserById(user.getId());
    }

    @Override
    public void removeUser(Long id) {
        userRepository.removeUser(id);
    }

    @Override
    public Collection<User> getUsers() {
        return userRepository.getUsers().values();
    }
}
