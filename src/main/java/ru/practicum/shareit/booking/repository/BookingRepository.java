package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking AS b " +
            "WHERE b.booker.id=?1 " +
            "ORDER BY b.start ASC")
    List<Booking> getAllBookingsOfUser(long userId);

    @Query("SELECT b FROM Booking AS b " +
            "WHERE b.booker.id=?1 " +
            "AND b.end<CURRENT_TIMESTAMP " +
            "ORDER BY b.start ASC")
    List<Booking> getPastBookingsOfUser(long userId);

    @Query("SELECT b FROM Booking AS b " +
            "WHERE b.booker.id=?1 " +
            "AND b.start>CURRENT_TIMESTAMP " +
            "AND b.end<CURRENT_TIMESTAMP " +
            "ORDER BY b.start ASC")
    List<Booking> getCurrentBookingsOfUser(long userId);

    @Query("SELECT b FROM Booking AS b " +
            "WHERE b.booker.id=?1 " +
            "AND b.start>CURRENT_TIMESTAMP " +
            "ORDER BY b.start ASC")
    List<Booking> getFutureBookingsOfUser(long userId);

    @Query("SELECT b FROM Booking AS b " +
            "WHERE b.booker.id=?1 " +
            "AND b.status='REJECTED' " +
            "ORDER BY b.start ASC")
    List<Booking> getRejectedBookingsOfUser(long userId);

    @Query("SELECT b FROM Booking AS b " +
            "WHERE b.booker.id=?1 " +
            "AND b.status='WAITING' " +
            "ORDER BY b.start ASC")
    List<Booking> getWaitingBookingsOfUser(long userId);

    @Query("SELECT b " +
            "FROM Booking AS b JOIN FETCH b.item AS i JOIN FETCH i.owner AS u " +
            "WHERE u.id=?1 " +
            "ORDER BY b.start ASC")
    List<Booking> getAllBookingsForUserItems(long userId);

    @Query("SELECT b " +
            "FROM Booking AS b JOIN FETCH b.item AS i JOIN FETCH i.owner AS u " +
            "WHERE u.id=?1 " +
            "AND b.end<CURRENT_TIMESTAMP " +
            "ORDER BY b.start ASC")
    List<Booking> getPastBookingsForUserItems(long userId);

    @Query("SELECT b " +
            "FROM Booking AS b JOIN FETCH b.item AS i JOIN FETCH i.owner AS u " +
            "WHERE u.id=?1 " +
            "AND b.start>CURRENT_TIMESTAMP " +
            "AND b.end<CURRENT_TIMESTAMP " +
            "ORDER BY b.start ASC")
    List<Booking> getCurrentBookingsForUserItems(long userId);

    @Query("SELECT b " +
            "FROM Booking AS b JOIN FETCH b.item AS i JOIN FETCH i.owner AS u " +
            "WHERE u.id=?1 " +
            "AND b.start>CURRENT_TIMESTAMP " +
            "ORDER BY b.start ASC")
    List<Booking> getFutureBookingsForUserItems(long userId);

    @Query("SELECT b " +
            "FROM Booking AS b JOIN FETCH b.item AS i JOIN FETCH i.owner AS u " +
            "WHERE u.id=?1 " +
            "AND b.status='REJECTED' " +
            "ORDER BY b.start ASC")
    List<Booking> getRejectedBookingsForUserItems(long userId);

    @Query("SELECT b " +
            "FROM Booking AS b JOIN FETCH b.item AS i JOIN FETCH i.owner AS u " +
            "WHERE u.id=?1 " +
            "AND b.status='WAITING' " +
            "ORDER BY b.start ASC")
    List<Booking> getWaitingBookingsForUserItems(long userId);

    @Query("SELECT CASE WHEN COUNT(b)> 0 THEN TRUE ELSE FALSE END "+
            "FROM Booking AS b " +
            "WHERE b.booker.id=?1 AND b.item.id=?2")
    boolean isUserBookedItem(long userId, long  itemId);

}
