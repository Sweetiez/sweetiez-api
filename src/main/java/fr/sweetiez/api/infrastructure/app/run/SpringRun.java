package fr.sweetiez.api.infrastructure.app.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan(basePackages = "fr.sweetiez.api")
@EnableJpaRepositories("fr.sweetiez.api.infrastructure")
@EntityScan("fr.sweetiez.api.infrastructure.repository")
@EnableWebSecurity
public class SpringRun {

    public static void main(String[] args) {
        SpringApplication.run(SpringRun.class, args);
    }

}
