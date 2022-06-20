package fr.sweetiez.api.core.loyalty.rewards.ports;

import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;

public interface RewardsWriter {

    Reward save(Reward reward);
}
