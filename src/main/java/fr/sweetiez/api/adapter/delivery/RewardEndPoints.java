package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.responses.RewardCreatedResponse;
import fr.sweetiez.api.core.loyalty.rewards.models.responses.RewardResponse;
import fr.sweetiez.api.core.loyalty.rewards.services.RewardService;
import org.springframework.http.ResponseEntity;

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
        var reward = rewardService.retrieveById(id);
        return ResponseEntity.ok(new RewardResponse(reward));
    }
}
