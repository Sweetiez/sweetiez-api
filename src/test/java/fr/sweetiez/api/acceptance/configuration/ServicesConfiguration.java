package fr.sweetiez.api.acceptance.configuration;

import fr.sweetiez.api.authentication.AuthenticationService;
import fr.sweetiez.api.authentication.InMemoryAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class ServicesConfiguration {

    @Bean
    @Scope("cucumber-glue")
    public AuthenticationService authenticationService() {
        return new InMemoryAuthenticationService();
    }

}
