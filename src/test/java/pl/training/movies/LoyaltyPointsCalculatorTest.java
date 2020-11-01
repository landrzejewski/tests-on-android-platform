package pl.training.movies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoyaltyPointsCalculatorTest {

    private final LoyaltyPointsCalculator loyaltyPoints = new LoyaltyPointsCalculator();

    private static Stream<Arguments> init() {
        return Stream.of(
                Arguments.of(true, 1, 1),
                Arguments.of(true, 2, 2),
                Arguments.of(false, 2, 1)
        );
    }

    @MethodSource("init")
    @ParameterizedTest
    @DisplayName("Should calculate loyalty points")
    public void shouldCalculateLoyaltyPoints(boolean isNewRelease, long periodInDays, int expectedResult) {
        assertEquals(expectedResult, loyaltyPoints.getPoints(isNewRelease, periodInDays));
    }

}
