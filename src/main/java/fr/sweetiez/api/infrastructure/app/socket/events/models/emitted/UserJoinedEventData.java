package fr.sweetiez.api.infrastructure.app.socket.events.models.emitted;

import fr.sweetiez.api.infrastructure.app.socket.events.models.received.PeerSignalData;

public record UserJoinedEventData(String callerID, PeerSignalData signal) {}
