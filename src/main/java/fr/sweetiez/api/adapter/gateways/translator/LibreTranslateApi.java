package fr.sweetiez.api.adapter.gateways.translator;

import fr.sweetiez.api.core.ingredients.models.TranslateRequest;
import fr.sweetiez.api.core.ingredients.ports.TranslatorApi;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class LibreTranslateApi implements TranslatorApi {

    private final String url;
    private final RestTemplate restTemplate;

    public LibreTranslateApi(RestTemplate restTemplate) {
        this.url = "https://libretranslate.de/translate";
        this.restTemplate = restTemplate;
    }

    public String translate(String query, String source, String target) {
        var requestBody = new TranslateRequest(query, source, target);

        try {
            var response = Objects.requireNonNull(
                    restTemplate.postForObject(url, requestBody, LibreTranslateResponse.class));
            return response.translatedText();
        }
        catch (RestClientException | NullPointerException exception) {
            return query;
        }
    }
}
