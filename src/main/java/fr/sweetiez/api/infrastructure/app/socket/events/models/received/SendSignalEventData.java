package fr.sweetiez.api.infrastructure.app.socket.events.models.received;

import java.util.UUID;

public record SendSignalEventData(UUID roomID, String userToSignal, String callerID, PeerSignalData signal) {}
