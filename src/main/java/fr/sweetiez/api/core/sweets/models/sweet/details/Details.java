package fr.sweetiez.api.core.sweets.models.sweet.details;

import fr.sweetiez.api.core.ingredients.models.Ingredients;

import java.util.ArrayList;
import java.util.Collection;

public record Details(Description description, Flavor flavor, Collection<String> images, Ingredients ingredients, double score) {
    public boolean isValid() {
        return description != null && flavor != null && images != null && ingredients.isValid();
    }

    public Details addImage(String imageUrl) {
        var imageList = new ArrayList<>(images);
        if (images.size() == 1 && images.toArray()[0].equals("")) {
            imageList = new ArrayList<>();
        }
        imageList.add(imageUrl);
        return new Details(this.description, this.flavor, imageList, this.ingredients, this.score);
    }
}
