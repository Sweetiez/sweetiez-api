package fr.sweetiez.api.core.events.use_case.face_to_face;

import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.space.SpaceDTO;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.AnimatorAdminResponse;
import fr.sweetiez.api.core.events.use_case.face_to_face.models.CreateSpaceRequest;

import java.util.Collection;

public class SpaceService {
    private final Spaces spaces;

    private final Animators animators;

    public SpaceService(Spaces spaces, Animators animators) {
        this.spaces = spaces;
        this.animators = animators;
    }

    public SpaceDTO create(CreateSpaceRequest request) {
        return spaces.save(new SpaceDTO(request));
    }

    public Collection<SpaceDTO> retrieveSpaces() {
        return spaces.findAll();
    }

    public Collection<AnimatorAdminResponse> retrieveAnimators() {
        return animators.findAll();
    }
}
