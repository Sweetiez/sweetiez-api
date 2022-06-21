package fr.sweetiez.api.core.loyalty.rewards.ports;

import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Rewards;

import java.util.UUID;

public interface RewardsReader {

    Reward findById(UUID id);

    Rewards findAll();
}
