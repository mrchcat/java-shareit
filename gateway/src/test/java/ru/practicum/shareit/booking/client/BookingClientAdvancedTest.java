package ru.practicum.shareit.booking.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingClientAdvancedTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    RestTemplateBuilder restTemplateBuilder;

    private BookingClient bookingClient;

    @BeforeEach
    void init(){
        when(restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(anyString()))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()).thenReturn(restTemplate);
        bookingClient=new BookingClient("http://localhost:9090", restTemplateBuilder);
    }


    @Test
    void createBooking() {
        long userId = 22;
        long itemId = 12312;
        BookingCreateDto bookingCreateDto = new BookingCreateDto(itemId, null, null);
        String path = "";
        Object body=new Object();
        ResponseEntity<Object> response=new ResponseEntity<>(body, HttpStatus.CREATED);
        when(restTemplate.exchange(
                anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Object>>any()))
                .thenReturn(response);
        assertDoesNotThrow(()->bookingClient.createBooking(userId,bookingCreateDto));
    }

    @Test
    void answerBookingRequest() {
        long userId = 22;
        long bookingId = 12312;
        boolean isApproved = true;
        String path = String.format("/%d", bookingId);
        Map<String, String> query = Map.of("approved", Boolean.TRUE.toString());
        assertThrows(Throwable.class, () -> bookingClient.answerBookingRequest(userId, bookingId, isApproved));
        assertThrows(Throwable.class, () -> bookingClient.patch(path, userId, query, null));

    }

    @Test
    void getBookingStatus() {
        long userId = 22;
        long bookingId = 12312;
        String path = String.format("/%d", bookingId);
        assertThrows(Throwable.class, () -> bookingClient.getBookingStatus(userId, bookingId));
        assertThrows(Throwable.class, () -> bookingClient.get(path, userId, null));
    }

    @Test
    void getAllBookingsOfUser() {
        long userId = 22;
        BookingState state = BookingState.ALL;
        String path = "";
        Map<String, String> query = Map.of("state", state.name());
        assertThrows(Throwable.class, () -> bookingClient.getAllBookingsOfUser(userId, state));
        assertThrows(Throwable.class, () -> bookingClient.get(path, userId, query));
    }

    @Test
    void getAllBookingsForUserItems() {
        String path = "/owner";
        long userId = 22;
        BookingState state = BookingState.ALL;
        Map<String, String> query = Map.of("state", state.name());
        assertThrows(Throwable.class, () -> bookingClient.getAllBookingsForUserItems(userId, state));
        assertThrows(Throwable.class, () -> bookingClient.get(path, userId, query));
    }
}