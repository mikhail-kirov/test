package ru.practicum.shareit.user.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Transactional
@SpringBootTest(properties = "jdbc.url=jdbc:h2:mem:shareit://localhost:8070/test",
                webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceBdTest {

    private final UserService userService;
    private final EntityManager em;

    @BeforeEach
    void setUp() {
        User user = new User(null,"Mike", "test@mail.com");
        em.persist(user);
        em.flush();
    }

    @Test
    void updateUserTest() {

        User userNew = new User(1L,"Mik", "test1@mail.com");

        userService.updateUser(userNew.getId(), userNew);

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        User userUpdated = query.setParameter("id", userNew.getId()).getSingleResult();

        assertThat(userUpdated.getId(), notNullValue());
        assertThat(userUpdated.getName(), equalTo("Mik"));
        assertThat(userUpdated.getEmail(), equalTo("test1@mail.com"));
    }

    @Test
    void getUserByIdTest() {

        User user = userService.getUserById(2L);

        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), equalTo("Mike"));
        assertThat(user.getEmail(), equalTo("test@mail.com"));
    }
}
