package fr.sweetiez.api.infrastructure.repository.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStepEntity, UUID> {

     List<RecipeStepEntity> findByRecipeId(UUID id);
}
