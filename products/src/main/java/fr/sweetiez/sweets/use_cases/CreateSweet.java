package fr.sweetiez.sweets.use_cases;

import fr.sweetiez.sweets.model.Sweet;
import fr.sweetiez.sweets.model.Sweets;


public class CreateSweet {

    private final Sweets sweets;

    public CreateSweet(Sweets sweets) {
        this.sweets = sweets;
    }

    public Sweet create(SweetDTO sweetDTO) {
        var existingSweets = this.sweets.all();
        var sweet = new Sweet(sweetDTO, existingSweets);
        this.sweets.save(sweet);

        return sweet;
    }
}
