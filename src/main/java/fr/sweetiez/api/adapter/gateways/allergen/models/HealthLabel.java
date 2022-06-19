package fr.sweetiez.api.adapter.gateways.allergen.models;

import java.util.Collection;
import java.util.Optional;

public enum HealthLabel {
    ALCOHOL_FREE("ALCOHOL_FREE"),
    CELERY_FREE("CELERY_FREE"),
    CRUSTACEAN_FREE("CRUSTACEAN_FREE"),
    DAIRY_FREE("DAIRY_FREE"),
    EGG_FREE("EGG_FREE"),
    FAT_FREE("FAT_FREE"),
    FISH_FREE("FISH_FREE"),
    GLUTEN_FREE("GLUTEN_FREE"),
    KETO_FRIENDLY("KETO_FRIENDLY"),
    KOSHER("KOSHER"),
    LOW_SUGAR("LOW_SUGAR"),
    LUPINE_FREE("LUPINE_FREE"),
    MILK_FREE("MILK_FREE"),
    MOLLUSK_FREE("MOLLUSK_FREE"),
    MUSTARD_FREE("MUSTARD_FREE"),
    NO_OIL_ADDED("NO_OIL_ADDED"),
    NO_SUGAR_ADDED("NO_SUGAR_ADDED"),
    PALEO("PALEO"),
    PEANUT_FREE("PEANUT_FREE"),
    PESCATARIAN("PESCATARIAN"),
    PORK_FREE("PORK_FREE"),
    RED_MEAT_FREE("RED_MEAT_FREE"),
    SESAME_FREE("SESAME_FREE"),
    SHELLFISH_FREE("SHELLFISH_FREE"),
    SOY_FREE("SOY_FREE"),
    SPECIFIC_CARBS("SPECIFIC_CARBS"),
    TREE_NUT_FREE("TREE_NUT_FREE"),
    VEGAN("VEGAN"),
    VEGETARIAN("VEGETARIAN"),
    WHEAT_FREE("WHEAT_FREE");

    private final String value;

    HealthLabel(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public Optional<String> getAllergen() {
        if (!value.contains("_") || this.equals(NO_OIL_ADDED) || this.equals(NO_SUGAR_ADDED) ||
                this.equals(FAT_FREE) || this.equals(KETO_FRIENDLY) || this.equals(SPECIFIC_CARBS) ||
                this.equals(DAIRY_FREE) || this.equals(ALCOHOL_FREE) || this.equals(GLUTEN_FREE) ||
                this.equals(LOW_SUGAR))
        {
            return Optional.empty();
        }

        if (this.equals(TREE_NUT_FREE) || this.equals(RED_MEAT_FREE)) {
            var splitValue = value.split("_");
            return Optional.of(splitValue[0] + " " + splitValue[1]);
        }

        return Optional.of(value.split("_")[0]);
    }

    public static Collection<String> getAllergens(Collection<HealthLabel> labels) {
        return labels.stream()
                .map(HealthLabel::getAllergen)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public Optional<String> getDiet() {
        if (this.equals(KETO_FRIENDLY)) {
            return Optional.of(value.split("_")[0]);
        }
        else if(this.equals(GLUTEN_FREE)) {
            var splitValue = value.split("_");
            return Optional.of(splitValue[0] + " " + splitValue[1]);
        }

        if (value.contains("_")) {
            return Optional.empty();
        }

        return Optional.of(value);
    }

    public static Collection<String> getDiets(Collection<HealthLabel> labels) {
        return labels.stream()
                .map(HealthLabel::getDiet)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
