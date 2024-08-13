package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.Item;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
            SELECT id,name,description,available,owner_id, request_id
            FROM items
            WHERE owner_id=?1
            """)
    Collection<Item> getAllItems(long userId);

    @Query("""
            SELECT id,name,description,available,owner_id, request_id
            FROM items
            WHERE available IS TRUE
                  AND (LOWER(name) like CONCAT('%', LOWER(?1), '%')
                  OR LOWER(description) like CONCAT('%', LOWER(?1) , '%'))
            """)
    Collection<Item> searchItems(String text);
}
