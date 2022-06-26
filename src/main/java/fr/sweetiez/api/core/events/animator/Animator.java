package fr.sweetiez.api.core.events.animator;

import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.use_case.exception.AnimatorNotAvailableException;
import fr.sweetiez.api.core.events.use_case.exception.OverlappingScheduleException;

import java.util.Objects;
import java.util.Set;

public class Animator {
    private final AnimatorID id;
    private final Set<Schedule> busySchedules;

    public Animator(AnimatorID id, Set<Schedule> busySchedules) {
        this.id = id;
        this.busySchedules = busySchedules;
    }

    public void book(Schedule schedule){
        try {
            schedule.checkAvailability(busySchedules);
            busySchedules.add(schedule);
        }
        catch (OverlappingScheduleException exception){
            throw new AnimatorNotAvailableException();
        }
    }

    public AnimatorID getId() {
        return id;
    }

    public Set<Schedule> getBusySchedules() {
        return busySchedules;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animator animator = (Animator) o;
        return id.equals(animator.id);
    }

    public int hashCode() {
        return Objects.hash(id, busySchedules);
    }
}
