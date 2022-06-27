package fr.sweetiez.api.adapter.repository.events;

import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.animator.AnimatorID;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;
import fr.sweetiez.api.infrastructure.repository.events.animator.AnimatorRepository;
import fr.sweetiez.api.infrastructure.repository.events.animator.ReservedAnimatorEntity;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class AnimatorsAdapter implements Animators {
    private final CustomerRepository customerRepository;
    private final AnimatorRepository animatorRepository;

    public AnimatorsAdapter(AnimatorRepository animatorRepository, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.animatorRepository = animatorRepository;
    }

    public Optional<Animator> findById(UUID id) {
        var customer = customerRepository.findById(id);
        var reservations = animatorRepository.findAllById(List.of(id))
                .stream()
                .map(reservedAnimator -> new Schedule(
                        reservedAnimator.getStart(),
                        reservedAnimator.getDuration())
                )
                .collect(Collectors.toList());

        return customer.isPresent()
                ? Optional.of(new Animator(new AnimatorID(id), reservations))
                : Optional.empty();
    }

    public void book(Animator animator, Schedule schedule) {
        animatorRepository.save(new ReservedAnimatorEntity(
                animator.getId().getAnimatorId(),
                schedule.getStart(),
                Duration.between(schedule.getStart(), schedule.getEnd())));
    }

    public void reschedule(Animator animator, Schedule schedule) {
        animatorRepository.save(new ReservedAnimatorEntity(
                animator.getId().getAnimatorId(),
                schedule.getStart(),
                Duration.between(schedule.getStart(), schedule.getEnd())));
    }
}
