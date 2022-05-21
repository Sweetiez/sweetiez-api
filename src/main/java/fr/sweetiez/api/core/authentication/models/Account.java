package fr.sweetiez.api.core.authentication.models;

import java.util.Collection;
import java.util.UUID;

public record Account(UUID id, String username, String password, Collection<Role> roles) {}
