package fr.sweetiez.api.infrastructure.repository.reward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, UUID> {
}
