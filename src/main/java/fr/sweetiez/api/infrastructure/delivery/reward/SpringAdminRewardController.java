package fr.sweetiez.api.infrastructure.delivery.reward;

import fr.sweetiez.api.adapter.delivery.RewardEndPoints;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.responses.RewardCreatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/rewards")
public class SpringAdminRewardController {
    private final RewardEndPoints rewardEndPoints;

    public SpringAdminRewardController(RewardEndPoints rewardEndPoints) {
        this.rewardEndPoints = rewardEndPoints;
    }

    @PostMapping()
    public ResponseEntity<RewardCreatedResponse> addReward(@RequestBody CreateRewardRequest request) {
        return rewardEndPoints.createReward(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReward(@PathVariable String id) {
        return rewardEndPoints.deleteReward(id);
    }



}
