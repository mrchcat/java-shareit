package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemDTOMapper {
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    public ItemOutputDTO toDTO(long userId, Item item) {
        Collection<Comment> comments = commentRepository.getCommentsByItem(item.getId());
        Collection<CommentOutputDTO> commentsDTO = comments.stream().map(CommentDTOMapper::toDTO).toList();
        Optional<Booking> lastBooking = bookingRepository.getLastBookingForItemOwnedByUser(userId, item.getId());
        Optional<Booking> nextBooking = bookingRepository.getNextBookingForItemOwnedByUser(userId, item.getId());
        return ItemOutputDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.isAvailable())
                .comments(commentsDTO)
                .lastBooking(lastBooking.map(Booking::getEnd).orElse(null))
                .nextBooking(nextBooking.map(Booking::getStart).orElse(null))
                .build();
    }

    public Item fromNewDTO(User user, ItemNewDTO itemNewDTO) {
        return Item.builder()
                .name(itemNewDTO.getName())
                .description(itemNewDTO.getDescription())
                .available(itemNewDTO.getAvailable())
                .owner(user)
                .build();
    }
}
