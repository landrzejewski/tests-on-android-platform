package pl.training.movies;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RentalService {

    private final PriceCalculator priceCalculator;
    private final LoyaltyPointsCalculator loyaltyPointsCalculator;

    public Statement createStatement(List<Rental> rentals) {
        double totalValue = rentals.stream()
                .mapToDouble(rental -> priceCalculator.getValueFor(rental.getType(), rental.periodInDays()))
                .sum();
        int loyaltyPoints = rentals.stream()
                .mapToInt(rental -> loyaltyPointsCalculator.getPoints(rental.isNewRelease(), rental.periodInDays()))
                .sum();
        return new Statement(totalValue, loyaltyPoints);
    }

}
