package fr.sweetiez.api.core.events.use_case.streaming;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;
import fr.sweetiez.api.core.events.use_case.streaming.models.responses.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RetrieveEvents {

    private final StreamingEvents events;
    private final CustomerReader customers;

    public RetrieveEvents(StreamingEvents events, CustomerReader customers) {
        this.events = events;
        this.customers = customers;
    }

    public List<EventResponse> retrievePublished() {
        var now = LocalDateTime.now();

        return this.events.findAllPublished()
                .stream()
                .filter(event -> event.schedule().getEnd().isAfter(now))
                .map(event -> new EventResponse(
                        event.id().eventId(),
                        event.title(),
                        event.description(),
                        new ScheduleResponse(event.schedule().getStart(), event.schedule().getEnd()),
                        new Availability(event.places(), event.subscribers().size()),
                        event.subscribers().stream().map(c -> c.id().value()).toList()
                ))
                .toList();
    }

    public List<EventAdminResponse> retrieveAll() {
        var now = LocalDateTime.now();
        return this.events.findAll()
                .stream()
                .filter(event -> event.schedule().getEnd().isAfter(now))
                .map(event -> {
                    var animatorId = event.animator().getId().getAnimatorId().toString();
                    var animator = this.customers.findById(new CustomerId(animatorId)).orElseThrow();

                    return new EventAdminResponse(
                            event.id().eventId(),
                            event.title(),
                            event.description(),
                            new ScheduleResponse(event.schedule().getStart(), event.schedule().getEnd()),
                            new Availability(event.places(), event.subscribers().size()),
                            new AnimatorResponse(animator.id().value(), animator.firstName(), animator.lastName()),
                            event.status()
                    );
                })
                .toList();
    }

    public List<EventResponse> retrieveMyEvents(UUID userId) {
        var events = this.events.findAll();
        var response = new ArrayList<EventResponse>();

        events.forEach(event -> {
            var subscribersId = event.subscribers().stream().map(Customer::id).toList();
            if (subscribersId.contains(new CustomerId(userId.toString()))) {
                response.add(new EventResponse(
                        event.id().eventId(),
                        event.title(),
                        event.description(),
                        new ScheduleResponse(event.schedule().getStart(), event.schedule().getEnd()),
                        new Availability(event.places(), event.subscribers().size()),
                        event.subscribers().stream().map(c -> c.id().value()).toList()
                ));
            }
        });

        return response;
    }

}
