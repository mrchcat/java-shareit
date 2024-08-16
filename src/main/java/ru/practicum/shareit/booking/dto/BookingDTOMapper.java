package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingDTOMapper {

    public static Booking fromNewDTO(User user, Item item, BookingCreateDto booking) {
        return Booking.builder()
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(item)
                .booker(user)
                .build();
    }

    public static BookingDto toDTO(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .status(booking.getStatus())
                .build();
    }

}
