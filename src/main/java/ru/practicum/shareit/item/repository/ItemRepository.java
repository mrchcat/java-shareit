package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.Item;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item AS i WHERE i.owner.id=?1")
    Collection<Item> getAllItems(long userId);

    @Query("SELECT i FROM Item AS i " +
            "WHERE i.available = TRUE " +
            "AND (LOWER(i.name) like CONCAT('%', LOWER(?1), '%') " +
            "OR LOWER(i.description) like CONCAT('%', LOWER(?1) , '%'))")
    Collection<Item> searchItems(String text);

    boolean existsByIdAndOwner_id(long itemId, long userId);
}
