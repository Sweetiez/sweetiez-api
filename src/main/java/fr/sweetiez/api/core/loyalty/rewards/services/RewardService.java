package fr.sweetiez.api.core.loyalty.rewards.services;

import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsReader;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsWriter;

public class RewardService {

    private final RewardsReader rewardsReader;

    private final RewardsWriter rewardsWriter;

    public RewardService(RewardsReader rewardsReader, RewardsWriter rewardsWriter) {
        this.rewardsReader = rewardsReader;
        this.rewardsWriter = rewardsWriter;
    }

}
