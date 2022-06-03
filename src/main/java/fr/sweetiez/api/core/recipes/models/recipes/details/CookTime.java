package fr.sweetiez.api.core.recipes.models.recipes.details;

/**
 * CookingTimes in minutes
 * @param preparationTime
 * @param chillTime
 * @param cookTime
 */
public record CookTime(Integer preparationTime, Integer chillTime, Integer cookTime) {
    public boolean isValid() {
        return this.preparationTime != null && this.preparationTime >=0 &&
                this.chillTime != null && this.chillTime >=0 &&
                this.cookTime != null && this.cookTime >=0;
    }
}
