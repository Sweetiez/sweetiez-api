package fr.sweetiez.api.infrastructure.app.config;

import fr.sweetiez.api.adapter.delivery.*;
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
import fr.sweetiez.api.core.reports.services.ReportService;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.SweetService;
import fr.sweetiez.api.infrastructure.app.security.TokenProvider;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleRepository;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;
import fr.sweetiez.api.infrastructure.repository.reports.ReportRepository;
import fr.sweetiez.api.infrastructure.repository.sweets.SweetRepository;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class SpringDependenciesConfig {
    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;

    private final SweetRepository sweetRepository;
    private final EvaluationRepository evaluationRepository;
    private final ReportRepository reportRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;

    public SpringDependenciesConfig(SweetRepository sweetRepository, EvaluationRepository evaluationRepository,
                                    ReportRepository reportRepository, CustomerRepository customerRepository,
                                    AccountRepository accountRepository, RoleRepository roleRepository,
                                    TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManager)
    {
        this.sweetRepository = sweetRepository;
        this.evaluationRepository = evaluationRepository;
        this.reportRepository = reportRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
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

    // ADAPTERS
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
        return new SweetService(sweetWriter(), sweetReader(), evaluationService());
    }

    @Bean
    public ReportService reportService() {
        return new ReportService(customerService(), evaluationService(), reportRepositoryPort());
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

    // MINIO
    @Bean
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .credentials(accessKey, secretKey)
                .endpoint(minioUrl)
                .build();
    }
}
