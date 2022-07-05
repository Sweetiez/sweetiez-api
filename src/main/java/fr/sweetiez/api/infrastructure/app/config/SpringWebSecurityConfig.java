package fr.sweetiez.api.infrastructure.app.config;

import fr.sweetiez.api.infrastructure.app.security.JwtFilter;
import fr.sweetiez.api.infrastructure.app.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    public SpringWebSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(false);

        configuration.setExposedHeaders(List.of("Authorization", "refresh-token"));

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.csrf().disable();

        httpSecurity.cors().configurationSource(corsConfigurationSource());

        httpSecurity.authorizeRequests()
                .antMatchers("/evaluations",
                        "/order/me",
                        "/user/**",
                        "/events/participate"
                        ).hasRole("USER")
                .antMatchers(
                        "/admin/**"
                ).hasRole("ADMIN")
                .antMatchers(
                        "/auth/**",
                        "/order/**",
                        "/payment/**",
                        "/recipes/**",
                        "/rewards/**",
                        "/sweets/published",
                        "/sweets/{\\d+}",
                        "/trays/{\\d+}",
                        "/events/**",
                        "/actuator/**"
                        ).permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(
                new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class);
    }
}
