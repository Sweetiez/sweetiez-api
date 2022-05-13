package fr.sweetiez.api.core.sweets.models.requests;

import fr.sweetiez.api.core.sweets.models.sweet.states.Highlight;

public record PublishSweetRequest(String id, Highlight highlight) {}
