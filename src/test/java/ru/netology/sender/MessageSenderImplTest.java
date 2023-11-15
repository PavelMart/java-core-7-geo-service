package ru.netology.sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageSenderImplTest {
    private static Stream<Arguments> sendDataProvider() {
        return Stream.of(
                Arguments.of(new Location("Moscow", Country.RUSSIA, "Lenina", 15), GeoServiceImpl.MOSCOW_IP, "Добро пожаловать"),
                Arguments.of(new Location("New York", Country.USA, " 10th Avenue", 32), GeoServiceImpl.NEW_YORK_IP, "Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource("sendDataProvider")
    public void sendTest(Location location, String ip, String expected) {
        var geoService = mock(GeoService.class);
        when(geoService.byIp(anyString()))
                .thenReturn(location);

        var localizationService = spy(LocalizationServiceImpl.class);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        String actual = messageSender.send(headers);

        assertEquals(expected, actual);
    }
}