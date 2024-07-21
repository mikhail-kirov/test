package ru.practicum.shareit.item.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Component
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOwnerId(Long id);

    @Query("select it " +
            "from Item as it " +
            "where it.name ilike %?1% " +
            "or it.description ilike %?1%" +
            "and it.available = true")
    List<Item> getItemBySearch(String search);
}
