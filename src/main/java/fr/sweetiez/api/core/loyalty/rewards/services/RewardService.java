package fr.sweetiez.api.core.loyalty.rewards.services;

import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.UpdateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Rewards;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsReader;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsWriter;
import fr.sweetiez.api.core.loyalty.rewards.services.exceptions.RewardNotFoundException;
import fr.sweetiez.api.core.orders.models.orders.products.ProductType;
import fr.sweetiez.api.core.products.models.common.ProductID;
import fr.sweetiez.api.core.products.services.SweetService;
import fr.sweetiez.api.core.recipes.models.recipes.details.State;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class RewardService {

    private final RewardsReader rewardsReader;

    private final RewardsWriter rewardsWriter;

    private final SweetService sweetService;

    public RewardService(RewardsReader rewardsReader, RewardsWriter rewardsWriter, SweetService sweetService) {
        this.rewardsReader = rewardsReader;
        this.rewardsWriter = rewardsWriter;
        this.sweetService = sweetService;
    }

    public Reward createReward(CreateRewardRequest request) {
        return rewardsWriter.save(new Reward(request));
    }

    public Reward retrieveById(String id) throws RewardNotFoundException{
        try {
            var reward = rewardsReader.findById(UUID.fromString(id));
            String name = "";
            String image = "";
            if (reward.product().type() == ProductType.SWEET) {
                var sweet = sweetService.retrieveDetailsOf(new ProductID(UUID.fromString(reward.product().id())));
                name = sweet.name();
                image = new ArrayList<>(sweet.images()).get(0);
            }
            return new Reward(reward, name, image);
        } catch (NoSuchElementException e) {
            throw new RewardNotFoundException();
        }
    }

    public boolean deleteReward(String id) throws RewardNotFoundException {
        rewardsWriter.delete(retrieveById(id));
        try {
            retrieveById(id);
        } catch (RewardNotFoundException e) {
            return true;
        }
        return false;
    }

    public Rewards retrieveAll() {
        var rewards = rewardsReader.findAll();
        return new Rewards(rewards.rewards().stream()
                .map(reward -> {
                    try {
                        return retrieveById(reward.id().value().toString());
                    } catch (RewardNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList());
    }

    public Reward publishReward(String id) throws RewardNotFoundException {
        var reward = retrieveById(id);
        return rewardsWriter.save(new Reward(reward, State.PUBLISHED));
    }

    public Reward unPublishReward(String id) throws RewardNotFoundException {
        var reward = retrieveById(id);
        return rewardsWriter.save(new Reward(reward, State.NON_PUBLISHED));
    }

    public Reward updateReward(UpdateRewardRequest request) throws RewardNotFoundException {
        var reward = retrieveById(request.id());
        var updateReward = new Reward(request, reward);
        return rewardsWriter.save(updateReward);
    }
}
