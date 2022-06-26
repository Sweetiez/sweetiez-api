package fr.sweetiez.api.core.events.event;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.schedule.Schedule;
import fr.sweetiez.api.core.events.space.Space;

import java.util.List;
import java.util.UUID;

public record EventDto(
        UUID id,
        String title,
        Animator animator,
        Schedule schedule,
        StatusEvent status,
        Space space,
        List<Customer>subscribers
) {}
