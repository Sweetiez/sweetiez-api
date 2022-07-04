package fr.sweetiez.api.infrastructure.delivery.events;

import fr.sweetiez.api.adapter.delivery.event.StreamingEventEndPoints;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvent;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.CreateStreamingEventRequestDTO;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.RescheduleEventRequest;
import fr.sweetiez.api.core.events.use_case.streaming.models.requests.SubscribeEventRequest;
import fr.sweetiez.api.core.events.use_case.streaming.models.responses.EventAdminResponse;
import fr.sweetiez.api.core.events.use_case.streaming.models.responses.EventResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
public class SpringStreamingEventController {

    private final StreamingEventEndPoints endPoints;

    public SpringStreamingEventController(StreamingEventEndPoints endPoints) {
        this.endPoints = endPoints;
    }

    @PostMapping("/admin/events/streaming")
    public ResponseEntity<?> create(@RequestBody CreateStreamingEventRequestDTO request) {
        return endPoints.createEvent(request);
    }

    @PutMapping("/admin/events/streaming/publish/{id}")
    public ResponseEntity<StreamingEvent> publish(@PathVariable UUID id) {
        return endPoints.publishEvent(id);
    }

    @PutMapping("/admin/events/streaming/cancel/{id}")
    public ResponseEntity<StreamingEvent> cancel(@PathVariable UUID id) {
        return endPoints.cancelEvent(id);
    }

    @PutMapping("/admin/events/streaming/reschedule")
    public ResponseEntity<StreamingEvent> reschedule(@RequestBody RescheduleEventRequest request) {
        return endPoints.rescheduleEvent(request);
    }

    @PutMapping("/events/streaming/subscribe")
    public ResponseEntity<StreamingEvent> subscribe(@RequestBody SubscribeEventRequest request) {
        return endPoints.subscribeEvent(request);
    }

    @GetMapping("/events/streaming/subscribers/{id}")
    public ResponseEntity<Collection<EventResponse>> retrieveMyEvents(@PathVariable UUID id) {
        return endPoints.retrieveMyEvents(id);
    }

    @GetMapping("/events/streaming")
    public ResponseEntity<Collection<EventResponse>> retrieveAllPublished() {
        return endPoints.retrieveAllPublished();
    }

    @GetMapping("/admin/events/streaming")
    public ResponseEntity<Collection<EventAdminResponse>> retrieveAll() {
        return endPoints.retrieveAll();
    }
}
