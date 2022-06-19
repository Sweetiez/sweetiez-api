package fr.sweetiez.api.core.products.models.common;

public record Description(String content) {

    public boolean isValid() {
        return !content.isEmpty();
    }
    public String shortContent() {
        return content.length() > 100 ? content.substring(0, 100) + "..." : content;
    }
}
