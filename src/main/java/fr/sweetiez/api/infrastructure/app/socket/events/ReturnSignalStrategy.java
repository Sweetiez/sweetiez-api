package fr.sweetiez.api.infrastructure.app.socket.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sweetiez.api.infrastructure.app.socket.events.models.Payload;
import fr.sweetiez.api.infrastructure.app.socket.events.models.Rooms;
import fr.sweetiez.api.infrastructure.app.socket.events.models.emitted.ReceivingReturnedSignalEventData;
import fr.sweetiez.api.infrastructure.app.socket.events.models.received.ReturningSignalEventData;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class ReturnSignalStrategy implements EventStrategy {
    private final Rooms rooms;
    private final ObjectMapper mapper;

    public ReturnSignalStrategy(Rooms rooms) {
        this.rooms = rooms;
        mapper = new ObjectMapper();
    }

    public Rooms execute(WebSocketSession session, Object payload) throws IOException {
        var data = mapper.convertValue(payload, ReturningSignalEventData.class);

        var emitters = rooms.users().get(data.roomID());

        var emitter = emitters.stream()
                .filter(webSocketSession -> webSocketSession.getId().equals(data.callerID()))
                .findFirst()
                .orElseThrow();

            var messageData = new ReceivingReturnedSignalEventData(data.signal(), session.getId());
            var message = new Payload("receiving returned signal", messageData);
            emitter.sendMessage(new TextMessage(mapper.writeValueAsString(message)));

        return rooms;
    }
}
