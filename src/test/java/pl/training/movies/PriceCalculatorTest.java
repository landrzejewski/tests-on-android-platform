package pl.training.movies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceCalculatorTest {

    private static final int  PERIOD_IN_DAYS = 6;

    private final PriceCalculator priceCalculator = new PriceCalculator();

    private static Stream<Arguments> init() {
        return Stream.of(
                Arguments.of(MovieType.REGULAR, 8),
                Arguments.of(MovieType.NEW_RELEASE, 18),
                Arguments.of(MovieType.CHILDREN, 6)
        );
    }

    @MethodSource("init")
    @ParameterizedTest
    @DisplayName("Should calculate movie price")
    public void shouldCalculateMoviePrice(MovieType movieType, double expectedPrice) {
        double price = priceCalculator.getValueFor(movieType, PERIOD_IN_DAYS);
        assertEquals(expectedPrice, price);
    }

}
