package fr.sweetiez.api.infrastructure.app.socket;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        sessions.add(session);
    }

    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        sessions.remove(session);
    }

    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws IOException {
        for (WebSocketSession webSocketSession : sessions) {
            if (!session.equals(webSocketSession)) {
                webSocketSession.sendMessage(message);
            }
        }
    }
}
