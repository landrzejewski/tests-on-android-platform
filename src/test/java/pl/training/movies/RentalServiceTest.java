package pl.training.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    private static final Statement EXPECTED_STATEMENT = new Statement(10.0, 1);

    private RentalService rentalService;
    @Mock
    private PriceCalculator priceCalculator;
    @Mock
    private LoyaltyPointsCalculator loyaltyPointsCalculator;

    @BeforeEach
    public void init() {
        when(priceCalculator.getValueFor(any(MovieType.class), anyLong())).thenReturn(EXPECTED_STATEMENT.getTotalValue());
        when(loyaltyPointsCalculator.getPoints(anyBoolean(), anyLong())).thenReturn(EXPECTED_STATEMENT.getLoyaltyPoints());
        rentalService = new RentalService(priceCalculator, loyaltyPointsCalculator);
    }

    @Test
    @DisplayName("Should calculate movie price")
    public void shouldCreateValidStatement() {
        Rental rental = new Rental(MovieType.NEW_RELEASE, LocalDate.now());
        Statement statement = rentalService.createStatement(singletonList(rental));
        assertEquals(EXPECTED_STATEMENT, statement);
    }

}
