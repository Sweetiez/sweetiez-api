package fr.sweetiez.api.core.loyalty.rewards.services;

import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsReader;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsWriter;

public class RewardService {

    private final RewardsReader rewardsReader;

    private final RewardsWriter rewardsWriter;

    public RewardService(RewardsReader rewardsReader, RewardsWriter rewardsWriter) {
        this.rewardsReader = rewardsReader;
        this.rewardsWriter = rewardsWriter;
    }

    public Reward createReward(CreateRewardRequest request) {
        Reward reward = new Reward(request);
        rewardsWriter.save(reward);
        return reward;
    }

}
