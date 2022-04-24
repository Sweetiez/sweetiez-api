package fr.sweetiez.sweets.use_cases;

import fr.sweetiez.sweets.domain.Priority;

import java.util.UUID;

public class PublishSweetRequest {
    private final UUID id;
    private final Priority priority;

    public PublishSweetRequest(UUID id, Priority priority) {
        this.id = id;
        this.priority = priority;
    }

    public String getId() {
        return id.toString();
    }

    public Priority getPriority() {
        return priority;
    }
}
