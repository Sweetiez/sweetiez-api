package fr.sweetiez.api.adapter.repository.events;

import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.animator.AnimatorID;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.AnimatorAdminResponse;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleEntity;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;
import fr.sweetiez.api.infrastructure.repository.events.animator.AnimatorRepository;
import fr.sweetiez.api.infrastructure.repository.events.animator.ReservedAnimatorEntity;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class AnimatorsAdapter implements Animators {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AnimatorRepository animatorRepository;

    public AnimatorsAdapter(AnimatorRepository animatorRepository, CustomerRepository customerRepository,
                            AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.animatorRepository = animatorRepository;
        this.accountRepository = accountRepository;
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

    @Override
    public Collection<AnimatorAdminResponse> findAll() {
//        var animatorRole = new RoleEntity(3L, "ANIMATOR");
        return accountRepository.findByRoles_Name("ANIMATOR").stream()
                .map(account -> new AnimatorAdminResponse(customerRepository.findByEmail(account.getUsername()).orElseThrow().getId(),
                        account.getUsername()
                        )
                )
                .toList();
    }

    public void reschedule(Animator animator, Schedule schedule) {
        animatorRepository.save(new ReservedAnimatorEntity(
                animator.getId().getAnimatorId(),
                schedule.getStart(),
                Duration.between(schedule.getStart(), schedule.getEnd())));
    }
}
