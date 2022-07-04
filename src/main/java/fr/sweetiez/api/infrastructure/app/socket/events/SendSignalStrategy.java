package fr.sweetiez.api.infrastructure.app.socket.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sweetiez.api.infrastructure.app.socket.events.models.Payload;
import fr.sweetiez.api.infrastructure.app.socket.events.models.Rooms;
import fr.sweetiez.api.infrastructure.app.socket.events.models.emitted.UserJoinedEventData;
import fr.sweetiez.api.infrastructure.app.socket.events.models.received.SendSignalEventData;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class SendSignalStrategy implements EventStrategy {
    private final Rooms rooms;
    private final ObjectMapper mapper;

    public SendSignalStrategy(Rooms rooms) {
        this.rooms = rooms;
        mapper = new ObjectMapper();
    }

    public Rooms execute(WebSocketSession session, Object payload) throws IOException {
        var data = mapper.convertValue(payload, SendSignalEventData.class);

        var emitter = rooms.users().get(data.roomID())
                .stream()
                .filter(webSocketSession -> webSocketSession.getId().equals(data.userToSignal()))
                .findFirst()
                .orElseThrow();

        var messageData = new UserJoinedEventData(session.getId(), data.signal());
        var message = new Payload("user joined", messageData);

        if (emitter.isOpen())
            emitter.sendMessage(new TextMessage(mapper.writeValueAsString(message)));

        return rooms;
    }


}
