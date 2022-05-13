package fr.sweetiez.api.core.comments.models;

import java.util.UUID;

public record Comment(String content, UUID author, UUID subject) {}
