package fr.sweetiez.api.infrastructure.delivery.events;

import fr.sweetiez.api.adapter.delivery.event.FaceToFaceEventEndPoints;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvent;
import fr.sweetiez.api.core.events.space.SpaceDTO;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
public class SpringFaceToFaceEventController {

    private final FaceToFaceEventEndPoints endPoints;

    public SpringFaceToFaceEventController(FaceToFaceEventEndPoints endPoints) {
        this.endPoints = endPoints;
    }

    @PostMapping("/admin/spaces")
    public ResponseEntity<SpaceDTO> createSpace(@RequestBody CreateSpaceRequest request) {
        return endPoints.createSpace(request);
    }

    @GetMapping("/admin/spaces")
    public ResponseEntity<Collection<SpaceDTO>> retrieveSpace() {
        return endPoints.retrieveSpaces();
    }

    @GetMapping("/admin/animators")
    public ResponseEntity<Collection<AnimatorAdminResponse>> retrieveAnimators() {
        return endPoints.retrieveAnimators();
    }

    @PostMapping("/admin/events/face-to-face")
    public ResponseEntity<?> create(@RequestBody CreateEventRequestDTO request) {
        return endPoints.createEvent(request);
    }

    @PutMapping("/admin/events/face-to-face/publish/{id}")
    public ResponseEntity<FaceToFaceEvent> publish(@PathVariable UUID id) {
        return endPoints.publishEvent(id);
    }

    @PutMapping("/admin/events/face-to-face/cancel/{id}")
    public ResponseEntity<FaceToFaceEvent> cancel(@PathVariable UUID id) {
        return endPoints.cancelEvent(id);
    }

    @PutMapping("/admin/events/face-to-face/reschedule")
    public ResponseEntity<FaceToFaceEvent> reschedule(@RequestBody RescheduleEventRequest request) {
        return endPoints.rescheduleEvent(request);
    }

    @PutMapping("/events/face-to-face/subscribe")
    public ResponseEntity<FaceToFaceEvent> subscribe(@RequestBody SubscribeEventRequest request) {
        return endPoints.subscribeEvent(request);
    }

    @GetMapping("/events/face-to-face")
    public ResponseEntity<Collection<EventResponse>> retrieveAllPublished() {
        return endPoints.retrieveAllPublished();
    }

    @GetMapping("/events/face-to-face/subscribers/{id}")
    public ResponseEntity<Collection<EventResponse>> retrieveMyEvents(@PathVariable UUID id) {
        return endPoints.retrieveMyEvents(id);
    }

    @GetMapping("/admin/events/face-to-face")
    public ResponseEntity<Collection<EventAdminResponse>> retrieveAll() {
        return endPoints.retrieveAll();
    }
}
