package fr.sweetiez.api.infrastructure.app.config;

import fr.sweetiez.api.adapter.delivery.SweetEndPoints;
import fr.sweetiez.api.adapter.repository.SweetReaderAdapter;
import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.adapter.repository.SweetWriterAdapter;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.SweetService;
import fr.sweetiez.api.infrastructure.repository.SweetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDependenciesConfig {

    private final SweetRepository repository;

    public SpringDependenciesConfig(SweetRepository repository) {
        this.repository = repository;
    }

    @Bean
    public SweetMapper sweetMapper() {
        return new SweetMapper();
    }

    @Bean
    public SweetsReader sweetReader() {
        return new SweetReaderAdapter(repository, sweetMapper());
    }

    @Bean
    public SweetsWriter sweetWriter() {
        return new SweetWriterAdapter(repository, sweetMapper());
    }
    
    @Bean
    public SweetService sweetService() {
        return new SweetService(sweetWriter(), sweetReader());
    }

    @Bean
    public SweetEndPoints sweetEndPoints() {
        return new SweetEndPoints(sweetService());
    }
}
