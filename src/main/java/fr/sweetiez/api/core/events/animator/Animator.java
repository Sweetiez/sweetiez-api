package fr.sweetiez.api.core.events.animator;

import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.use_case.exception.AnimatorNotAvailableException;
import fr.sweetiez.api.core.events.use_case.exception.OverlappingScheduleException;
import fr.sweetiez.api.core.events.use_case.exception.SpaceNotAvailableException;

import java.util.List;
import java.util.Objects;

public class Animator {
    private final AnimatorID id;
    private final List<Schedule> busySchedules;

    public Animator(AnimatorID id, List<Schedule> busySchedules) {
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

    public void reschedule(Schedule currentSchedule, Schedule newSchedule){
        try {
            newSchedule.checkAvailability(busySchedules);
            busySchedules.remove(currentSchedule);
            busySchedules.add(newSchedule);
        }
        catch (OverlappingScheduleException exception){
            throw new SpaceNotAvailableException();
        }
    }

    public AnimatorID getId() {
        return id;
    }

    public List<Schedule> getBusySchedules() {
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
