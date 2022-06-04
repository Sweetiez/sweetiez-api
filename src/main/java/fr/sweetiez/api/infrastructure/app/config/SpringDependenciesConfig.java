package fr.sweetiez.api.infrastructure.app.config;

import fr.sweetiez.api.adapter.delivery.*;
import fr.sweetiez.api.adapter.gateways.allergen.EdamamApi;
import fr.sweetiez.api.adapter.gateways.translator.LibreTranslateApi;
import fr.sweetiez.api.adapter.repository.*;
import fr.sweetiez.api.adapter.shared.*;
import fr.sweetiez.api.core.authentication.ports.AuthenticationRepository;
import fr.sweetiez.api.core.authentication.services.AuthenticationService;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.customers.ports.CustomerWriter;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.evaluations.ports.EvaluationReader;
import fr.sweetiez.api.core.evaluations.ports.EvaluationWriter;
import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.ingredients.ports.IngredientApi;
import fr.sweetiez.api.core.ingredients.ports.Ingredients;
import fr.sweetiez.api.core.ingredients.ports.TranslatorApi;
import fr.sweetiez.api.core.ingredients.services.IngredientService;
import fr.sweetiez.api.core.reports.services.ReportService;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.SweetService;
import fr.sweetiez.api.infrastructure.app.security.TokenProvider;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleRepository;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;
import fr.sweetiez.api.infrastructure.repository.ingredients.HealthPropertyRepository;
import fr.sweetiez.api.infrastructure.repository.ingredients.IngredientRepository;
import fr.sweetiez.api.infrastructure.repository.reports.ReportRepository;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetRepository;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringDependenciesConfig {
    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${apis.edamam.app-id}")
    private String edamamAppId;

    @Value("${apis.edamam.app-key}")
    private String edamamAppKey;

    private final SweetRepository sweetRepository;
    private final EvaluationRepository evaluationRepository;
    private final ReportRepository reportRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final IngredientRepository ingredientRepository;
    private final HealthPropertyRepository healthPropertyRepository;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;

    public SpringDependenciesConfig(SweetRepository sweetRepository, EvaluationRepository evaluationRepository,
                                    ReportRepository reportRepository, CustomerRepository customerRepository,
                                    AccountRepository accountRepository, RoleRepository roleRepository,
                                    TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManager,
                                    IngredientRepository ingredientRepository,
                                    HealthPropertyRepository healthPropertyRepository)
    {
        this.sweetRepository = sweetRepository;
        this.evaluationRepository = evaluationRepository;
        this.reportRepository = reportRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.ingredientRepository = ingredientRepository;
        this.healthPropertyRepository = healthPropertyRepository;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // MAPPERS
    @Bean
    public EvaluationMapper evaluationMapper() {
        return new EvaluationMapper();
    }

    @Bean
    public AccountMapper accountMapper() {
        return new AccountMapper();
    }

    @Bean
    public CustomerMapper customerMapper() {
        return new CustomerMapper(accountMapper());
    }

    @Bean
    public SweetMapper sweetMapper() {
        return new SweetMapper();
    }

    @Bean
    public ReportMapper reportMapper() {
        return new ReportMapper();
    }

    @Bean
    public IngredientMapper ingredientMapper() {
        return new IngredientMapper();
    }

    // REPOSITORY ADAPTERS
    @Bean
    public EvaluationReader evaluationReader() {
        return new EvaluationReaderAdapter(evaluationRepository, evaluationMapper());
    }

    @Bean
    public EvaluationWriter evaluationWriter() {
        return new EvaluationWriterAdapter(evaluationRepository, evaluationMapper());
    }

    @Bean
    public CustomerReader customerReader() {
        return new CustomerReaderAdapter(customerRepository, customerMapper());
    }

    @Bean
    public CustomerWriter customerWriter() {
        return new CustomerWriterAdapter(customerRepository, customerMapper());
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
    public AuthenticationRepository authenticationRepository() {
        return new AccountRepositoryAdapter(accountRepository, roleRepository, accountMapper());
    }

    @Bean
    public fr.sweetiez.api.core.reports.ports.ReportRepository reportRepositoryPort() {
        return new ReportRepositoryAdapter(reportRepository, reportMapper());
    }

    @Bean
    public Ingredients ingredientRepositoryPort() {
        return new IngredientRepositoryAdapter(ingredientRepository, healthPropertyRepository, ingredientMapper());
    }

    // GATEWAY ADAPTERS
    @Bean
    public TranslatorApi translatorGateway() {
        return new LibreTranslateApi(restTemplate());
    }

    @Bean
    public IngredientApi ingredientGateway() {
        return new EdamamApi(edamamAppId, edamamAppKey, restTemplate());
    }

    // SERVICES
    @Bean
    public EvaluationService evaluationService() {
        return new EvaluationService(evaluationReader(), evaluationWriter(), customerService());
    }

    @Bean
    public AuthenticationService authenticationService() {
        return new AuthenticationService(authenticationRepository(), customerService(), tokenProvider, authenticationManager);
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(customerReader(), customerWriter());
    }

    @Bean
    public SweetService sweetService() {
        return new SweetService(sweetWriter(), sweetReader(), evaluationService(), customerService());
    }

    @Bean
    public ReportService reportService() {
        return new ReportService(customerService(), evaluationService(), reportRepositoryPort());
    }

    @Bean
    public IngredientService ingredientService() {
        return new IngredientService(translatorGateway(), ingredientGateway(), ingredientRepositoryPort());
    }

    // END POINTS
    @Bean
    public EvaluationEndPoints evaluationEndPoints() {
        return new EvaluationEndPoints(evaluationService());
    }

    @Bean
    public SweetEndPoints sweetEndPoints() {
        return new SweetEndPoints(sweetService());
    }

    @Bean
    public AuthenticationEndPoints authenticationEndPoints() {
        return new AuthenticationEndPoints(authenticationService());
    }

    @Bean
    public AdminSweetEndPoints adminSweetEndPoints() {
        return new AdminSweetEndPoints(sweetService(), minioClient());
    }

    @Bean
    public ReportEndPoints reportEndPoints() {
        return new ReportEndPoints(reportService());
    }

    @Bean
    public IngredientEndPoints ingredientEndPoints() {
        return new IngredientEndPoints(ingredientService());
    }

    // MINIO
    @Bean
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .credentials(accessKey, secretKey)
                .endpoint(minioUrl)
                .build();
    }
}
