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

        System.out.println("SESSION");
        System.out.println(session.getId());
        System.out.println("USERS IN ROOM:");
        System.out.println(emitters);

        System.out.println("CALLER ID: ");
        System.out.println(data.callerID());

        var optionalEmitter = emitters.stream()
                .filter(webSocketSession -> webSocketSession.getId().equals(data.callerID()))
                .findFirst();


        if (optionalEmitter.isPresent()) {
            System.out.println("I WAS HERE !");
            var messageData = new ReceivingReturnedSignalEventData(data.signal(), session.getId());
            var message = new Payload("receiving returned signal", messageData);
            optionalEmitter.get().sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        }
        else {
            System.out.println("F**K");
        }

        return rooms;
    }
}
