package fr.sweetiez.api.core.events.use_case.streaming;

import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;

public class AttendMasterclass {

    private final StreamingEvents streamingEvents;

    public AttendMasterclass(StreamingEvents streamingEvents) {
        this.streamingEvents = streamingEvents;
    }

    public boolean attendMasterclass(AttendMasterClassRequest request) {
        var canAttend = false;
        var optionalEvent = streamingEvents.findById(request.eventID());

        if (optionalEvent.isPresent()) {
            var subscribersID = optionalEvent.get()
                    .subscribers()
                    .stream()
                    .map(customer -> customer.id().value())
                    .toList();

            if (subscribersID.contains(request.participantID())) {
                canAttend = true;
            }
        }

        return canAttend;
    }
}
