package fr.sweetiez.api.core.loyalty.rewards.ports;

import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;

import java.util.UUID;

public interface RewardsReader {

    Reward findById(UUID id);
}
