package ru.practicum.shareit.booking.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.baseclient.BaseClient;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    public BookingClient(@Value("${shareit-server.url}") String serverUrl,
                         RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> createBooking(long userId, BookingCreateDto booking) {
        String path = "";
        return post(path, userId, booking);
    }

    public ResponseEntity<Object> answerBookingRequest(long userId, long bookingId, boolean isApproved) {
        String path = String.format("/%d", bookingId);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("approved", isApproved);
        return patch(path, userId, parameters, null);
    }

    public ResponseEntity<Object> getBookingStatus(long userId, long bookingId) {
        String path = String.format("/%d", bookingId);
        return get(path, userId);
    }

    public ResponseEntity<Object> getAllBookingsOfUser(long userId, BookingState state) {
        String path = "";
        Map<String, Object> parameters = Map.of("state", state.name());
        return get(path, userId, parameters);
    }

    public ResponseEntity<Object> getAllBookingsForUserItems(long userId, BookingState state) {
        String path = "/owner";
        Map<String, Object> parameters = Map.of("state", state.name());
        return get(path, userId, parameters);
    }
}
