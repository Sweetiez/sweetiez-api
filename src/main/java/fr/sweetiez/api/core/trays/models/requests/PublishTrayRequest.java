package fr.sweetiez.api.core.trays.models.requests;

import fr.sweetiez.api.core.trays.models.tray.states.Highlight;

public record PublishTrayRequest(String id, Highlight highlight) {}
