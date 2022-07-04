package fr.sweetiez.api.infrastructure.app.socket.events.models.received;

import java.util.UUID;

public record JoinRoomEventData(UUID roomID, String customerID) {}
