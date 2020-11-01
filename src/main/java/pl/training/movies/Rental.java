package pl.training.movies;

import lombok.Value;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Value
public class Rental {

    MovieType type;
    LocalDate startDate;

    public boolean isNewRelease() {
        return type == MovieType.NEW_RELEASE;
    }

    public long periodInDays() {
        return DAYS.between(LocalDate.now(), startDate);
    }

}
