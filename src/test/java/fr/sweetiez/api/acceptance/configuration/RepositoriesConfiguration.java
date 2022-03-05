package fr.sweetiez.api.acceptance.configuration;

import fr.sweetiez.api.customer.adapters.InMemoryCustomerRepository;
import fr.sweetiez.api.customer.adapters.InMemorySweetRepository;
import fr.sweetiez.api.customer.domain.CustomerRepository;
import fr.sweetiez.api.sweet.SweetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RepositoriesConfiguration {

    @Bean
    @Scope("cucumber-glue")
    public CustomerRepository customerRepository() {
        return new InMemoryCustomerRepository();
    }

    @Bean
    @Scope("cucumber-glue")
    public SweetRepository sweetRepository() {
        return new InMemorySweetRepository();
    }

}
