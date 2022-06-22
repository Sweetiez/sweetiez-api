package fr.sweetiez.api.core.loyalty.rewards.services;

import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Rewards;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsReader;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsWriter;
import fr.sweetiez.api.core.loyalty.rewards.services.exceptions.RewardNotFoundException;
import fr.sweetiez.api.core.orders.models.orders.products.ProductType;
import fr.sweetiez.api.core.recipes.models.recipes.details.State;
import fr.sweetiez.api.core.sweets.services.SweetService;

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
                var sweet = sweetService.retrieveSweetDetails(reward.product().id());
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
}
