package fr.sweetiez.api.infrastructure.app.socket.events;

import fr.sweetiez.api.infrastructure.app.socket.events.models.Rooms;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface EventStrategy {
    Rooms execute(WebSocketSession session, Object payload) throws IOException;
}
