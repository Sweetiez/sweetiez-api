package fr.sweetiez.api.core.products.models.common.details;

import fr.sweetiez.api.core.products.models.common.details.characteristics.Characteristics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public record Details(Collection<String> images, Characteristics characteristics, Valuation valuation) {
    public boolean isValid() {
        return images != null && characteristics != null && valuation != null;
    }

    public Details addImage(String imageUrl) {
        var imageList = new ArrayList<>(images);

        if (images.size() == 1 && images.toArray()[0].equals("")) {
            imageList = new ArrayList<>();
        }

        imageList.add(imageUrl);
        return new Details(imageList, characteristics, valuation);
    }

    public Details deleteImage(String imageUrl) {
        var updatedImages = images.stream()
                .filter(image -> !image.equals(imageUrl))
                .collect(Collectors.toSet());

        return new Details(updatedImages, characteristics, valuation);
    }
}
