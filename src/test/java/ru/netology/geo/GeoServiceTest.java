package ru.netology.geo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

public class GeoServiceTest {

    private static Stream<Arguments> biIpDataProvider() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.1.2.3", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.1.12.32", new Location("New York", Country.USA, null,  0))
        );
    }

    @ParameterizedTest
    @MethodSource("biIpDataProvider")
    public void byIpTest(String ip, Location expected) {
        var geoService = spy(GeoServiceImpl.class);

        var actual = geoService.byIp(ip);

        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getStreet(), actual.getStreet());
    }
}
