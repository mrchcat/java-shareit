package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.BookingState;
import ru.practicum.shareit.booking.dto.BookingNewDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;

import java.util.List;

public interface BookingService {

    BookingOutputDto createBooking(long userId, BookingNewDto booking);
    BookingOutputDto answerBookingRequest(long userId, long bookingId, boolean isApproved);

    BookingOutputDto getBookingStatus(long userId, long bookingId);

    List<BookingOutputDto> getAllBookingsOfUser(long userId, BookingState state);

    List<BookingOutputDto> getAllBookingsForUserItems(long userId, BookingState state);

}
