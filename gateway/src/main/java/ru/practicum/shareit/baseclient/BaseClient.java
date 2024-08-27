package ru.practicum.shareit.baseclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Slf4j
public class BaseClient {
    private final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    public ResponseEntity<Object> get(String path, Long userId, @Nullable Map<String, String> query) {
        return makeAndSendRequest(HttpMethod.GET, path, userId, query, null);
    }

    public <T> ResponseEntity<Object> post(String path, Long userId, @Nullable Map<String, String> query, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, userId, query, body);
    }

    public <T> ResponseEntity<Object> patch(String path, Long userId, @Nullable Map<String, String> query, T body) {
        return makeAndSendRequest(HttpMethod.PATCH, path, userId, query, body);
    }

    public void delete(String path) {
        makeAndSendRequest(HttpMethod.DELETE, path, null, null, null);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method,
                                                          String path,
                                                          @Nullable Long userId,
                                                          @Nullable Map<String, String> query,
                                                          @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders(userId));
        ResponseEntity<Object> shareitServerResponse;
        try {
            UriComponents uriComponents;
            if (query == null) {
                uriComponents = UriComponentsBuilder.fromPath(path).build();
            } else {
                MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
                for (var entry : query.entrySet()) {
                    queryMap.add(entry.getKey(), entry.getValue());
                }
                uriComponents = UriComponentsBuilder.fromPath(path).queryParams(queryMap).build();
            }
            String uri = uriComponents.toString();
            shareitServerResponse = rest.exchange(uri, method, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(shareitServerResponse);
    }

    private HttpHeaders defaultHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (userId != null) {
            headers.set("X-Sharer-User-Id", String.valueOf(userId));
        }
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
