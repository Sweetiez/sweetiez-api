package fr.sweetiez.api.core.events.events.streaming_event;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.events.animator.Animator;
import fr.sweetiez.api.core.events.events.StatusEvent;
import fr.sweetiez.api.core.events.schedule.Schedule;

import java.util.List;
import java.util.UUID;

public record StreamingEventDto(
        UUID id,
        String title,
        String description,
        Animator animator,
        Schedule schedule,
        StatusEvent status,
        int places,
        List<Customer>subscribers
) {}
