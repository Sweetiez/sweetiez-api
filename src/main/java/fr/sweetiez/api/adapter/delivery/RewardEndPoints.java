package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.PublishRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.UnPublishRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.UpdateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.responses.RewardCreatedResponse;
import fr.sweetiez.api.core.loyalty.rewards.models.responses.RewardResponse;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;
import fr.sweetiez.api.core.loyalty.rewards.services.RewardService;
import fr.sweetiez.api.core.loyalty.rewards.services.exceptions.RewardNotFoundException;
import fr.sweetiez.api.core.recipes.models.recipes.details.State;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class RewardEndPoints {
    private final RewardService rewardService;

    public RewardEndPoints(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    public ResponseEntity<RewardCreatedResponse> createReward(CreateRewardRequest request) {
        var reward = rewardService.createReward(request);
        return ResponseEntity.ok(new RewardCreatedResponse(reward));
    }

    public ResponseEntity<RewardResponse> retrieveReward(String id) {
        try {
            var reward = rewardService.retrieveById(id);
            return ResponseEntity.ok(new RewardResponse(reward));
        } catch (RewardNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> deleteReward(String id) {
        try {
            rewardService.deleteReward(id);
            return ResponseEntity.ok(id);
        } catch (RewardNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Collection<RewardResponse>> retrieveAllPublishedRewards() {
        var rewards = rewardService.retrieveAll();
        return ResponseEntity.ok(rewards.rewards().stream()
                .filter(r -> r.state() == State.PUBLISHED)
                .map(RewardResponse::new)
                .toList());
    }

    public ResponseEntity<Collection<RewardResponse>> retrieveAllRewards() {
        var rewards = rewardService.retrieveAll();
        return ResponseEntity.ok(rewards.rewards().stream()
                .map(RewardResponse::new)
                .toList());
    }

    public ResponseEntity<RewardResponse> publishReward(PublishRewardRequest request) {
        try {
            var reward = rewardService.publishReward(request.id());
            return ResponseEntity.ok(new RewardResponse(reward));
        } catch (RewardNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RewardResponse> unPublishReward(UnPublishRewardRequest request) {
        try {
            var reward = rewardService.unPublishReward(request.id());
            return ResponseEntity.ok(new RewardResponse(reward));
        } catch (RewardNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RewardCreatedResponse> updateReward(UpdateRewardRequest request) {
        try {
            var reward = rewardService.updateReward(request);
            return ResponseEntity.ok(new RewardCreatedResponse(reward));
        } catch (RewardNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
