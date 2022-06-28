package fr.sweetiez.api.infrastructure.delivery.reward;

import fr.sweetiez.api.adapter.delivery.RewardEndPoints;
import fr.sweetiez.api.core.loyalty.rewards.models.responses.RewardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/rewards")
public class SpringRewardController {

    private final RewardEndPoints rewardEndPoints;

    public SpringRewardController(RewardEndPoints rewardEndPoints) {
        this.rewardEndPoints = rewardEndPoints;
    }

    @GetMapping()
    public ResponseEntity<Collection<RewardResponse>> getRewards() {
        return rewardEndPoints.retrieveAllPublishedRewards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardResponse> getReward(@PathVariable String id) {
        return rewardEndPoints.retrieveReward(id);
    }
}
