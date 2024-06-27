package ru.practicum.shareit.user.data;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    public void setUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public void removeUser(Long id) {
        users.remove(id);
    }
}
