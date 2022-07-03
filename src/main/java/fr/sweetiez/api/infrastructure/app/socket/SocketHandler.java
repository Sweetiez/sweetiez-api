package fr.sweetiez.api.infrastructure.app.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sweetiez.api.infrastructure.app.socket.events.*;
import fr.sweetiez.api.infrastructure.app.socket.events.models.Rooms;
import fr.sweetiez.api.infrastructure.app.socket.events.models.emitted.ConnectedEvent;
import fr.sweetiez.api.infrastructure.app.socket.events.models.Payload;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class SocketHandler extends TextWebSocketHandler {

    Rooms rooms = new Rooms();
    EventStrategy eventStrategy;
    ObjectMapper mapper = new ObjectMapper();

    // connect
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws IOException {
        var message = new ConnectedEvent("connected", session.getId());
        session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
    }

    private void broadcastEvent(List<WebSocketSession> room, String eventName, Object data) {
        room.forEach(user -> {
            var message = new Payload(eventName, data);
            try {
                user.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // disconnect
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        var roomID = rooms.socketToRoom().get(session.getId());
        var room = rooms.users().get(roomID);

        if (room == null) return;

        room = room.stream()
                .filter(webSocketSession -> !webSocketSession.getId().equals(session.getId()))
                .collect(Collectors.toList());

        rooms.users().replace(roomID, room);
        broadcastEvent(room, "user left", session.getId());
    }

    public void handleTextMessage(
            @NotNull WebSocketSession session,
            @NotNull TextMessage message) throws IOException
    {
        var payload = mapper.readValue(message.getPayload(), Payload.class);

        // todo: add try catch for error management
        switch (payload.eventName()) {
            case "join room" -> {
                System.out.println("=========================================================================================");
                System.out.println("BEFORE JOINING ROOM");
                System.out.println("=========================================================================================");
                System.out.println("\nROOMS NUMBER: " + rooms.users().keySet().size());
                System.out.println("\nROOMS:");
                System.out.println(rooms.users());
                System.out.println("\nCURRENT SESSION: ");
                System.out.println(session.getId());
                System.out.println();

                eventStrategy = new JoinRoomStrategy(rooms);
            }
            case "send signal" -> eventStrategy = new SendSignalStrategy(rooms);
            case "return signal" -> eventStrategy = new ReturnSignalStrategy(rooms);
        }

        rooms = eventStrategy.execute(session, payload.data());

        System.out.println("=========================================================================================");
        System.out.println("AFTER");
        System.out.println("=========================================================================================");
        System.out.println("\nROOMS NUMBER: " + rooms.users().keySet().size());
        System.out.println("\nROOMS:");
        System.out.println(rooms.users());
        System.out.println("\nCURRENT SESSION: ");
        System.out.println(session.getId());
        System.out.println();

    }
}
