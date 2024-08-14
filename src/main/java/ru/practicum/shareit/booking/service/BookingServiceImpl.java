package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingState;
import ru.practicum.shareit.booking.dto.BookingDTOMapper;
import ru.practicum.shareit.booking.dto.BookingNewDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.IdNotFoundException;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.Validator;

import java.util.List;

import static ru.practicum.shareit.booking.BookingStatus.APPROVED;
import static ru.practicum.shareit.booking.BookingStatus.REJECTED;
import static ru.practicum.shareit.booking.BookingStatus.WAITING;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final Validator validator;

    @Override
    public BookingOutputDto createBooking(long userId, BookingNewDto newBooking) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(String.format("User with id=%d does not exists", userId)));
        long itemId = newBooking.getItemId();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException(String.format("Item with id=%d does not exists", itemId)));
        if(!item.isAvailable()) {
            throw new InternalServerException(String.format("Item with id=%d is not available", itemId));
        }
        Booking booking = BookingDTOMapper.fromNewDTO(user, item, newBooking);
        booking.setStatus(WAITING);
        Booking createdBooking = bookingRepository.save(booking);
        return BookingDTOMapper.toDTO(createdBooking);
    }

    @Override
    public BookingOutputDto answerBookingRequest(long userId, long bookingId, boolean isApproved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IdNotFoundException(String.format("Booking with id=%d does not exists", bookingId)));
        if (booking.getItem().getOwner().getId() != userId) {
            throw new InternalServerException(String.format("User with id=%d does not have booking with id=%d", userId, bookingId));
        }
        booking.setStatus(isApproved ? APPROVED : REJECTED);
        Booking savedBooking = bookingRepository.save(booking);
        return BookingDTOMapper.toDTO(savedBooking);
    }

    @Override
    public BookingOutputDto getBookingStatus(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IdNotFoundException(String.format("Booking with id=%d does not exists", bookingId)));
        if ((booking.getBooker().getId() != userId) && (booking.getItem().getOwner().getId() != userId)) {
            throw new IdNotFoundException(String.format("User with id=%d is not owner or booker for booking with id=%d", userId, bookingId));
        }
        return BookingDTOMapper.toDTO(booking);
    }

    @Override
    public List<BookingOutputDto> getAllBookingsOfUser(long userId, BookingState state) {
        validator.validateIfUserNotExists(userId);
        List<Booking> bookings = switch (state) {
            case ALL -> bookingRepository.getAllBookingsOfUser(userId);
            case PAST -> bookingRepository.getPastBookingsOfUser(userId);
            case CURRENT ->bookingRepository.getCurrentBookingsOfUser(userId);
            case FUTURE -> bookingRepository.getFutureBookingsOfUser(userId);
            case REJECTED-> bookingRepository.getRejectedBookingsOfUser(userId);
            case WAITING -> bookingRepository.getWaitingBookingsOfUser(userId);
        };
        return bookings.stream()
                .map(BookingDTOMapper::toDTO)
                .toList();
    }

    @Override
    public List<BookingOutputDto> getAllBookingsForUserItems(long userId, BookingState state) {
        validator.validateIfUserNotExists(userId);
        List<Booking> bookings = switch (state) {
            case ALL -> bookingRepository.getAllBookingsForUserItems(userId);
            case PAST -> bookingRepository.getPastBookingsForUserItems(userId);
            case CURRENT ->bookingRepository.getCurrentBookingsForUserItems(userId);
            case FUTURE -> bookingRepository.getFutureBookingsForUserItems(userId);
            case REJECTED-> bookingRepository.getRejectedBookingsForUserItems(userId);
            case WAITING -> bookingRepository.getWaitingBookingsForUserItems(userId);
        };
        return bookings.stream()
                .map(BookingDTOMapper::toDTO)
                .toList();
    }
}