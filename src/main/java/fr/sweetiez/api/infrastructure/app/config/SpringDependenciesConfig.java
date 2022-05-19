package fr.sweetiez.api.infrastructure.app.config;

import fr.sweetiez.api.adapter.delivery.EvaluationEndPoints;
import fr.sweetiez.api.adapter.delivery.SweetEndPoints;
import fr.sweetiez.api.adapter.repository.*;
import fr.sweetiez.api.adapter.shared.EvaluationMapper;
import fr.sweetiez.api.adapter.shared.CustomerMapper;
import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.core.evaluations.ports.EvaluationReader;
import fr.sweetiez.api.core.evaluations.ports.EvaluationWriter;
import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.SweetService;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDependenciesConfig {

    private final SweetRepository sweetRepository;
    private final EvaluationRepository evaluationRepository;
    private final CustomerRepository customerRepository;

    public SpringDependenciesConfig(SweetRepository sweetRepository, EvaluationRepository evaluationRepository, CustomerRepository customerRepository) {
        this.sweetRepository = sweetRepository;
        this.evaluationRepository = evaluationRepository;
        this.customerRepository = customerRepository;
    }

    @Bean
    public EvaluationMapper evaluationMapper() {
        return new EvaluationMapper();
    }

    @Bean
    public EvaluationReader evaluationReader() {
        return new EvaluationReaderAdapter(evaluationRepository, evaluationMapper());
    }

    @Bean
    public EvaluationWriter evaluationWriter() {
        return new EvaluationWriterAdapter(evaluationRepository, evaluationMapper());
    }

    @Bean
    public EvaluationService evaluationService() {
        return new EvaluationService(evaluationReader(), evaluationWriter(), customerService());
    }

    @Bean
    public CustomerMapper customerMapper() {
        return new CustomerMapper();
    }

    @Bean
    public CustomerReader customerReader() {
        return new CustomerReaderAdapter(customerRepository, customerMapper());
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(customerReader());
    }

    @Bean
    public EvaluationEndPoints evaluationEndPoints() {
        return new EvaluationEndPoints(evaluationService());
    }
    @Bean
    public SweetMapper sweetMapper() {
        return new SweetMapper();
    }

    @Bean
    public SweetsReader sweetReader() {
        return new SweetReaderAdapter(sweetRepository, sweetMapper());
    }

    @Bean
    public SweetsWriter sweetWriter() {
        return new SweetWriterAdapter(sweetRepository, sweetMapper());
    }
    
    @Bean
    public SweetService sweetService() {
        return new SweetService(sweetWriter(), sweetReader(), evaluationService());
    }

    @Bean
    public SweetEndPoints sweetEndPoints() {
        return new SweetEndPoints(sweetService());
    }
}
