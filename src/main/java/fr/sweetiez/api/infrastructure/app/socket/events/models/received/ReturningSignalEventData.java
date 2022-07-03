package fr.sweetiez.api.infrastructure.app.socket.events.models.received;

import java.util.UUID;

public record ReturningSignalEventData(UUID roomID, String callerID, PeerSignalData signal) {}
