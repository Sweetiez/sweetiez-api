package fr.sweetiez.api.core.recipes.models.requests;

public record UpdateStepRequest(String id,
                                Integer order,
                                String description) {
}
