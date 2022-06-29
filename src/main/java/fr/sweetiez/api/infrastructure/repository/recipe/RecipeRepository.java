package fr.sweetiez.api.infrastructure.repository.recipe;

import fr.sweetiez.api.core.recipes.models.recipes.details.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, UUID> {

    Integer countByState(State state);

}
