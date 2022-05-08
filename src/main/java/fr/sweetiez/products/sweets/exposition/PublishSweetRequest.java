package fr.sweetiez.products.sweets.exposition;

import fr.sweetiez.products.common.Highlight;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class PublishSweetRequest {
    private final UUID id;
    private final Highlight highlight;
    private final UUID employee;
}
