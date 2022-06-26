package fr.sweetiez.api.infrastructure.delivery.events;

import fr.sweetiez.api.adapter.delivery.event.FaceToFaceEventEndPoints;
import fr.sweetiez.api.core.events.use_case.models.CreateEventRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringFaceToFaceEventController {

    private final FaceToFaceEventEndPoints endPoints;

    public SpringFaceToFaceEventController(FaceToFaceEventEndPoints endPoints) {
        this.endPoints = endPoints;
    }

    @PostMapping("/admin/events/face-to-face")
    public ResponseEntity<?> create(@RequestBody CreateEventRequestDTO request) {

        System.out.println(request);

        return endPoints.createEvent(request);
    }
}
