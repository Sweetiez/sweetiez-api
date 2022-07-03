package fr.sweetiez.api.infrastructure.app.socket.events.models.emitted;

import fr.sweetiez.api.infrastructure.app.socket.events.models.received.PeerSignalData;

public record ReceivingReturnedSignalEventData(PeerSignalData signal, String id) {}
