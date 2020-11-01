package pl.training.movies;

public class LoyaltyPointsCalculator {

    private static final int DEFAULT_POINTS = 1;
    private static final int EXTRA_POINTS = 1;
    private static final int DAYS_REQUIRED_FOR_EXTRA_POINTS = 2;

    public int getPoints(boolean isNewRelease, long periodInDays) {
        return isExtraScored(isNewRelease, periodInDays) ? DEFAULT_POINTS + EXTRA_POINTS : DEFAULT_POINTS;
    }

    private boolean isExtraScored(boolean isNewRelease, long periodInDays) {
        return isNewRelease && periodInDays >= DAYS_REQUIRED_FOR_EXTRA_POINTS;
    }

}
