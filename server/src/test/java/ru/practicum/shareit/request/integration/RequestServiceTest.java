package ru.practicum.shareit.request.integration;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.MappingItem;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.MappingRequest;
import ru.practicum.shareit.request.dto.ReqRequestDto;
import ru.practicum.shareit.request.dto.ReqResponseDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest(properties = "jdbc.url=jdbc:h2:mem:shareit://localhost:8070/test",
                webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Rollback(value = false)
public class RequestServiceTest {

    private final RequestService requestService;
    private final EntityManager em;
    private List<ReqRequestDto> requests;

    @BeforeEach
    void setUp() {

        User user1 = new User(null, "Mike", "test1@mail.com");
        em.persist(user1);

        requests = List.of(new ReqRequestDto("sdfgsdfg"),
                           new ReqRequestDto("sdthdrhu"));

        List<Request> requestList = requests.stream()
                .map(req -> MappingRequest.mapToRequest(1L, req))
                .toList();
        requestList.forEach(em::persist);

        ReqRequestDto requestDto = new ReqRequestDto("yfghghfd");
        em.persist(MappingRequest.mapToRequest(2L, requestDto));

        ItemDto itemDto = ItemDto.builder()
                .name("sdfas")
                .description("tujfyu")
                .available(true)
                .requestId(1L)
                .build();
        em.persist(MappingItem.mapToItem(itemDto, 2L));
        em.flush();
    }

    @Test
    void getRequestsByUserIdTest() {
        List<ReqResponseDto> reqResponse = requestService.getRequestsByUserId(1L);

        assertThat(reqResponse, hasSize(2));

        for (ReqRequestDto requestDto : requests) {
            assertThat(reqResponse, hasItem(allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("description", equalTo(requestDto.getDescription())),
                    hasProperty("created", notNullValue()),
                    hasProperty("items", equalTo(List.of()))
            )));
        }
    }
}
