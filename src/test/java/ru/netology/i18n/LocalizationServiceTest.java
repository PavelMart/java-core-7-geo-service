package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

public class LocalizationServiceTest {
    private static Stream<Arguments> localeDataProvider() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource("localeDataProvider")
    public void localeTest(Country country, String expected) {
        var localizationService = spy(LocalizationServiceImpl.class);

        var actual = localizationService.locale(country);

        assertEquals(expected, actual);
    }
}
