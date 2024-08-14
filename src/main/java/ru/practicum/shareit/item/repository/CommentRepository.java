package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.Comment;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT com FROM Comment AS com WHERE com.item.id=?1")
    Collection<Comment> getCommentsByItem(long itemId);

    @Query("SELECT km " +
           "FROM User AS u " +
           "JOIN FETCH Item AS i ON u.id=i.owner.id " +
           "JOIN FETCH Comment AS km ON km.item.id=i.id " +
           "WHERE u.id=?1")
    Collection<Comment> getCommentsByUserItems(long itemId);


}
