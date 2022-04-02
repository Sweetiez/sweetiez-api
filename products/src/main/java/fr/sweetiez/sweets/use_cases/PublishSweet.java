package fr.sweetiez.sweets.use_cases;

import fr.sweetiez.sweets.domain.Priority;
import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.Sweets;

public class PublishSweet {

    private final Sweets sweets;

    public PublishSweet(Sweets sweets) {
        this.sweets = sweets;
    }

    public Sweet publish(String id, Priority priority) {
        var optional = sweets.findByID(id);
        if (optional.isEmpty()) {
            throw new AnySweetFoundException();
        }
        Sweet sweet = optional.get();

        Sweet publishedSweet = new Sweet(sweet, priority);

        sweets.save(publishedSweet);

        return publishedSweet;
    }
}
