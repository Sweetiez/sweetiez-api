package fr.sweetiez.api.acceptance.configuration;

import fr.sweetiez.api.authentication.AuthenticationService;
import fr.sweetiez.api.authentication.InMemoryAuthenticationService;
import fr.sweetiez.api.usecases.payment.InMemoryPaymentService;
import fr.sweetiez.api.usecases.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class ServicesConfiguration {

    @Bean
    @Scope("cucumber-glue")
    public AuthenticationService authenticationService() {
        return new InMemoryAuthenticationService();
    }

    @Bean
    @Scope("cucumber-glue")
    public PaymentService paymentService() {
        return new InMemoryPaymentService();
    }
}
