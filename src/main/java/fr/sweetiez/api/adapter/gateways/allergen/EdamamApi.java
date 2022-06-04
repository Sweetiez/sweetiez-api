package fr.sweetiez.api.adapter.gateways.allergen;

import fr.sweetiez.api.adapter.gateways.allergen.models.HealthLabel;
import fr.sweetiez.api.adapter.gateways.allergen.models.requests.IngredientNutrition;
import fr.sweetiez.api.adapter.gateways.allergen.models.requests.IngredientNutritionRequest;
import fr.sweetiez.api.adapter.gateways.allergen.models.responses.EdamamNutrientsResponse;
import fr.sweetiez.api.adapter.gateways.allergen.models.responses.EdamamParsedResponse;
import fr.sweetiez.api.core.ingredients.ports.IngredientApi;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class EdamamApi implements IngredientApi {

    private final String url;
    private final RestTemplate restTemplate;
    private final Map<String, String> credentials;

    public EdamamApi(String appId, String appKey, RestTemplate restTemplate) {
        this.url = "https://api.edamam.com/api/food-database/v2";
        this.restTemplate = restTemplate;

        this.credentials = new HashMap<>();
        this.credentials.put("app_id", appId);
        this.credentials.put("app_key", appKey);
    }

    private String prepareUrl(String baseUrl, Map<String, String> parameters) {
        var stringBuilder = new StringBuilder(baseUrl + "?");

        parameters.keySet().forEach(key -> stringBuilder
                .append(key)
                .append("=")
                .append(parameters.get(key))
                .append("&"));

        return stringBuilder
                .deleteCharAt(stringBuilder.lastIndexOf("&"))
                .toString();
    }

    public String findIdByName(String name) {
        try {
            var parameters = new HashMap<>(credentials);
            parameters.put("ingr", name);

            var url = prepareUrl(this.url + "/parser", parameters);

            var response = Objects.requireNonNull(
                    restTemplate.getForObject(url, EdamamParsedResponse.class));

            if (response.parsed().isEmpty()) {
                throw new IngredientNotFoundException();
            }

            return response.parsed().get(0).food().foodId();
        }
        catch (RestClientException | NullPointerException exception) {
            throw new IngredientNotFoundException();
        }
    }

    public Collection<HealthLabel> getHealthLabelsOf(String ingredientId) {
        try {
            var ingredient = new IngredientNutrition(1, "", ingredientId);
            var requestBody = new IngredientNutritionRequest(List.of(ingredient));
            var url = prepareUrl(this.url + "/nutrients", credentials);
            var response = Objects.requireNonNull(restTemplate.postForObject(
                    url,
                    requestBody,
                    EdamamNutrientsResponse.class));

            var healthLabels = new ArrayList<HealthLabel>();
            response.healthLabels().forEach(label -> healthLabels.add(HealthLabel.valueOf(label)));

            return healthLabels;
        }
        catch (RestClientException | NullPointerException exception) {
            throw new IngredientNotFoundException();
        }
    }
}
