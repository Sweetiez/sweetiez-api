package fr.sweetiez.api.core.events.use_case.streaming;

import java.util.UUID;

public record AttendMasterClassRequest(UUID eventID, String participantID) {}
