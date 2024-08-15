package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Comment;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT k " +
           "FROM Comment AS k " +
           "WHERE k.item.id=?1")
    Collection<Comment> getCommentsByItem(long itemId);

//    @Query("SELECT k " +
//           "FROM Comment AS k "+
//           "JOIN FETCH Item AS i "+
//           "JOIN FETCH User AS u " +
//           "WHERE u.id=:userId")
//    Collection<Comment> getCommentsByUserItems(long userId);
}
