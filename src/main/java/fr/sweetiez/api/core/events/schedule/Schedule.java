package fr.sweetiez.api.core.events.schedule;

import fr.sweetiez.api.core.events.use_case.exception.EventDateIsPastException;
import fr.sweetiez.api.core.events.use_case.exception.OverlappingScheduleException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Schedule {
    private final LocalDateTime start;
    private final Duration duration;

    public Schedule(LocalDateTime start, Duration duration) {
        this.start = start;
        this.duration = duration;
    }

    public LocalDateTime getEnd() {
        return start.plus(duration);
    }

    public boolean isOverlapping(Schedule schedule) {
        return !(schedule.getStart().isAfter(getEnd())
                || schedule.getEnd().isBefore(getStart()));
    }

    public void checkDateAnterior() {
        if(LocalDateTime.now().isAfter(getEnd())){
            throw new EventDateIsPastException();
        }
    }

    public void checkAvailability(List<Schedule> schedules) throws OverlappingScheduleException {
        checkDateAnterior();

        for (Schedule schedule : schedules) {
            if (isOverlapping(schedule)) {
                throw new OverlappingScheduleException();
            }
        }
    }

    public LocalDateTime getStart() {
        return start;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(start, schedule.start) && Objects.equals(duration, schedule.duration);
    }

    public int hashCode() {
        return Objects.hash(start, duration);
    }
}
