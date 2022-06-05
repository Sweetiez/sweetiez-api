package fr.sweetiez.api.core.trays.models.tray.details;

public record Description(String content) {

    public String shortContent() {
        return content.length() > 100 ? content.substring(0, 100) + "..." : content;
    }
}
