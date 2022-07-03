package fr.sweetiez.api.infrastructure.app.socket.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sweetiez.api.infrastructure.app.socket.events.models.Payload;
import fr.sweetiez.api.infrastructure.app.socket.events.models.Rooms;
import fr.sweetiez.api.infrastructure.app.socket.events.models.received.JoinRoomEventData;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class JoinRoomStrategy implements EventStrategy {
    private final Rooms rooms;
    private final ObjectMapper mapper;
    private final int ROOM_LIMIT;

    public JoinRoomStrategy(Rooms rooms) {
        this.rooms = rooms;
        mapper = new ObjectMapper();
        ROOM_LIMIT = 5;
    }

    public Rooms execute(WebSocketSession session, Object payload) throws IOException {
        var data = mapper.convertValue(payload, JoinRoomEventData.class);

        if (rooms.users().containsKey(data.roomID())) {
            var participants = rooms.users().get(data.roomID());

            if (participants.size() == ROOM_LIMIT) {
                var message = new Payload("room full", null);
                session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));

                return rooms;
            }

            participants.add(session);
        }
        else {
            var sessions = new CopyOnWriteArrayList<>(List.of(session));
            rooms.users().put(data.roomID(), sessions);
        }

        rooms.socketToRoom().put(session.getId(), data.roomID());
        var usersInThisRoom = rooms.users().get(data.roomID())
                .stream()
                .map(WebSocketSession::getId)
                .filter(id -> !id.equals(session.getId()))
                .collect(Collectors.toList());

        var message = new Payload("all users", usersInThisRoom);
        session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));

        return rooms;
    }
}
