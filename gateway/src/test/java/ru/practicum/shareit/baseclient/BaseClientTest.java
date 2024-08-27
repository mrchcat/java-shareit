package ru.practicum.shareit.baseclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BaseClientTest {

    @Mock
    private RestTemplate rest;

    @InjectMocks
    private BaseClient baseClient;

    @Test
    public void getTest() {
        assertThrows(Throwable.class, () -> baseClient.get(null, null, null));
    }

    @Test
    public void postTest() {
        assertThrows(Throwable.class, () -> baseClient.post(null, null, null, null));
    }

    @Test
    public void patchTest() {
        assertThrows(Throwable.class, () -> baseClient.patch(null, null, null, null));
    }

    @Test
    public void deleteTest() {
        assertThrows(Throwable.class, () -> baseClient.delete(null));
    }
}