package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookingStatus {
    WAITING,
    APPROVED,
    REJECTED,
    CANCELED;
}
