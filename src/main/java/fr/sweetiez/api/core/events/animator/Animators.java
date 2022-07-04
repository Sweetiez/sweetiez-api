package fr.sweetiez.api.core.events.animator;

import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.AnimatorAdminResponse;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface Animators {

    Optional<Animator> findById(UUID id);
    void book(Animator animator, Schedule schedule);

    Collection<AnimatorAdminResponse> findAll();
}
