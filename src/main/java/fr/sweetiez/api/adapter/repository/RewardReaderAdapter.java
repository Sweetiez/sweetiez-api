package fr.sweetiez.api.adapter.repository;

import fr.sweetiez.api.adapter.shared.RewardMapper;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Reward;
import fr.sweetiez.api.core.loyalty.rewards.models.rewards.Rewards;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsReader;
import fr.sweetiez.api.infrastructure.repository.reward.RewardRepository;

import java.util.UUID;

public class RewardReaderAdapter implements RewardsReader {

    private final RewardRepository repository;

    private final RewardMapper mapper;


    public RewardReaderAdapter(RewardRepository repository, RewardMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Reward findById(UUID id) {
        var reward = repository.findById(id);
        return mapper.toDto(reward.orElseThrow());
    }

    @Override
    public Rewards findAll() {
        return new Rewards(repository.findAll().stream()
                .map(mapper::toDto)
                .toList());
    }
}
