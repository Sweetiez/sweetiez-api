package fr.sweetiez.api.core.recipes.models.requests;

public record CreateStepRequest (String id,
                                 Integer order,
                                 String description) {

}
