package fr.sweetiez.api.core.events.use_case.face_to_face;

import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvents;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RetrieveEvents {

    private final FaceToFaceEvents events;
    private final Spaces spaces;
    private final CustomerReader customers;

    public RetrieveEvents(FaceToFaceEvents events, Spaces spaces, CustomerReader customers) {
        this.events = events;
        this.spaces = spaces;
        this.customers = customers;
    }

    public List<EventResponse> retrievePublished() {
        var events = this.events.findAllPublished();
        var response = new ArrayList<EventResponse>();
        var now = LocalDateTime.now();

        events.forEach(event -> {
            if (event.getSchedule().getEnd().isAfter(now)) {
                var space = this.spaces.getInfo(event.getSpace().getId().getSpaceID()).orElseThrow();
                response.add(new EventResponse(
                        event.getId().eventId(),
                        event.getTitle(),
                        event.getDescription(),
                        new Location(space.id(), space.address(), space.zipCode(), space.city()),
                        new ScheduleResponse(event.getSchedule().getStart(), event.getSchedule().getEnd()),
                        new Availability(space.places(), event.getSubscribers().size()),
                        event.getSubscribers().stream().map(c -> c.id().value()).toList()
                ));
            }
        });

        return response;
    }

    public List<EventAdminResponse> retrieveAll() {
        var events = this.events.findAll();
        var response = new ArrayList<EventAdminResponse>();
        var now = LocalDateTime.now();

        events.forEach(event -> {
            if (event.getSchedule().getEnd().isAfter(now)) {
                var space = this.spaces.getInfo(event.getSpace().getId().getSpaceID()).orElseThrow();
                var animator = this.customers.findById(
                        new CustomerId(event.getAnimator().getId().getAnimatorId().toString()))
                        .orElseThrow();

                response.add(new EventAdminResponse(
                        event.getId().eventId(),
                        event.getTitle(),
                        event.getDescription(),
                        new Location(space.id(), space.address(), space.zipCode(), space.city()),
                        new ScheduleResponse(event.getSchedule().getStart(), event.getSchedule().getEnd()),
                        new Availability(space.places(), event.getSubscribers().size()),
                        new AnimatorResponse(animator.id().value(), animator.firstName(), animator.lastName()),
                        event.getStatus())
                );
            }
        });

        return response;
    }
}
