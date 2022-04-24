package fr.sweetiez.sweets.use_cases;

import fr.sweetiez.sweets.domain.Sweet;
import fr.sweetiez.sweets.domain.Sweets;


public class CreateSweet {

    private final Sweets sweets;

    public CreateSweet(Sweets sweets) {
        this.sweets = sweets;
    }

    public Sweet create(CreateSweetRequest request) {
        var existingSweets = this.sweets.all();
        var sweet = new Sweet(request, existingSweets);
        System.out.println(sweet);
        this.sweets.save(sweet, request.getCreator());

        return sweet;
    }
}
