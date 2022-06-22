package fr.sweetiez.api.infrastructure.delivery.reward;

import fr.sweetiez.api.adapter.delivery.RewardEndPoints;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.CreateRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.PublishRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.requests.UnPublishRewardRequest;
import fr.sweetiez.api.core.loyalty.rewards.models.responses.RewardCreatedResponse;
import fr.sweetiez.api.core.loyalty.rewards.models.responses.RewardResponse;
import fr.sweetiez.api.core.recipes.models.requests.PublishRecipeRequest;
import fr.sweetiez.api.core.recipes.models.requests.UnPublishRecipeRequest;
import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
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

    @PutMapping("/publish")
    public ResponseEntity<RewardResponse> publishRecipe(@RequestBody PublishRewardRequest request) {
        return rewardEndPoints.publishReward(request);
    }

    @DeleteMapping("/publish")
    public ResponseEntity<RewardResponse> unPublishRecipe(@RequestBody UnPublishRewardRequest request) {
        return rewardEndPoints.unPublishReward(request);
    }

}
