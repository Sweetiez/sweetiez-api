package fr.sweetiez.api.infrastructure.delivery.events;

import fr.sweetiez.api.adapter.delivery.event.FaceToFaceEventEndPoints;
import fr.sweetiez.api.core.events.event.Event;
import fr.sweetiez.api.core.events.use_case.models.CreateEventRequestDTO;
import fr.sweetiez.api.core.events.use_case.models.RescheduleEventRequest;
import fr.sweetiez.api.core.events.use_case.models.SubscribeEventRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SpringFaceToFaceEventController {

    private final FaceToFaceEventEndPoints endPoints;

    public SpringFaceToFaceEventController(FaceToFaceEventEndPoints endPoints) {
        this.endPoints = endPoints;
    }

    @PostMapping("/admin/events/face-to-face")
    public ResponseEntity<?> create(@RequestBody CreateEventRequestDTO request) {
        return endPoints.createEvent(request);
    }

    @PutMapping("/admin/events/face-to-face/publish/{id}")
    public ResponseEntity<Event> publish(@PathVariable UUID id) {
        return endPoints.publishEvent(id);
    }

    @PutMapping("/admin/events/face-to-face/cancel/{id}")
    public ResponseEntity<Event> cancel(@PathVariable UUID id) {
        return endPoints.cancelEvent(id);
    }

    @PutMapping("/admin/events/face-to-face/reschedule")
    public ResponseEntity<Event> reschedule(@RequestBody RescheduleEventRequest request) {
        return endPoints.rescheduleEvent(request);
    }

    @PutMapping("/admin/events/face-to-face/subscribe")
    public ResponseEntity<Event> subscribe(@RequestBody SubscribeEventRequest request) {
        return endPoints.subscribeEvent(request);
    }
}
