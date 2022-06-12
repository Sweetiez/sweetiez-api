package fr.sweetiez.api.core.sweets.models.sweet.details;

import fr.sweetiez.api.core.evaluations.models.Evaluation;
import fr.sweetiez.api.core.ingredients.models.Ingredient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public record Details(
        Description description,
        Flavor flavor,
        Collection<String> images,
        Collection<Ingredient> ingredients,
        Collection<Evaluation> evaluations) {
    public boolean isValid() {
        return description != null && flavor != null && images != null && ingredients != null;
    }

    public Details addImage(String imageUrl) {
        var imageList = new ArrayList<>(images);
        if (images.size() == 1 && images.toArray()[0].equals("")) {
            imageList = new ArrayList<>();
        }
        imageList.add(imageUrl);
        return new Details(this.description, this.flavor, imageList, this.ingredients, this.evaluations);
    }

    public Details deleteImage(String imageUrl) {
        var updatedImages = images.stream()
                .filter(image -> !image.equals(imageUrl))
                .collect(Collectors.toSet());

        return new Details(this.description, this.flavor, updatedImages, this.ingredients, this.evaluations);
    }
}
