package fr.sweetiez.api.acceptance.configuration;

import fr.sweetiez.api.account.CustomerAccountRepository;
import fr.sweetiez.api.account.InMemoryCustomerAccountRepository;
import fr.sweetiez.api.customer.adapters.InMemoryCustomerRepository;
import fr.sweetiez.api.customer.adapters.InMemorySweetRepository;
import fr.sweetiez.api.customer.domain.CustomerRepository;
import fr.sweetiez.api.order.InMemoryOrderRepository;
import fr.sweetiez.api.order.OrderRepository;
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

    @Bean
    @Scope("cucumber-glue")
    public CustomerAccountRepository customerAccountRepository() {
        return new InMemoryCustomerAccountRepository();
    }

    @Bean
    @Scope("cucumber-glue")
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }
}
