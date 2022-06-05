package fr.sweetiez.api.core.trays.models.responses;

import java.util.Map;

public record Evaluation(Double mark, int voters, Map<Integer, Integer> votes) {}
