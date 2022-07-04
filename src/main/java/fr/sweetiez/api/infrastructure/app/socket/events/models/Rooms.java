package fr.sweetiez.api.infrastructure.app.socket.events.models;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record Rooms(
        Map<UUID, List<WebSocketSession>> users,
        Map<String, UUID> socketToRoom)
{
    public Rooms() {
        this(new HashMap<>(), new HashMap<>());
    }
}
