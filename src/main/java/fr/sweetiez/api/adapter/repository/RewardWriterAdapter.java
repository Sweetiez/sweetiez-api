package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.RewardMapper;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsWriter;
import fr.sweetiez.api.infrastructure.repository.reward.RewardRepository;

public class RewardWriterAdapter implements RewardsWriter {
    private final RewardRepository repository;

    private final RewardMapper mapper;


    public RewardWriterAdapter(RewardRepository repository, RewardMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Reward save(Reward reward) {
        return mapper.toDto(repository.save(mapper.toEntity(reward)));
    }
}
