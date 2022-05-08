package fr.sweetiez.products.sweets.exposition;

import fr.sweetiez.products.common.Highlight;

import java.util.UUID;

public record PublishSweetRequest(UUID id, Highlight highlight, UUID employee) {}
