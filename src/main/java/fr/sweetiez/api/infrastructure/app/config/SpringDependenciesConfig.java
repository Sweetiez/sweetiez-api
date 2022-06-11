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
import fr.sweetiez.api.core.orders.ports.OrdersReader;
import fr.sweetiez.api.core.orders.ports.OrdersWriter;
import fr.sweetiez.api.core.orders.services.OrderService;
import fr.sweetiez.api.core.recipes.ports.RecipeReader;
import fr.sweetiez.api.core.recipes.ports.RecipeWriter;
import fr.sweetiez.api.core.recipes.services.RecipeService;
import fr.sweetiez.api.core.reports.services.ReportService;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.SweetService;
import fr.sweetiez.api.infrastructure.app.security.TokenProvider;
import fr.sweetiez.api.infrastructure.payements.StripePaymentService;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleRepository;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderDetailRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderRepository;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeRepository;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeStepRepository;
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

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.secret-endpoint}")
    private String stripeEndpointSecret;

    private final SweetRepository sweetRepository;
    private final EvaluationRepository evaluationRepository;
    private final ReportRepository reportRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;

    private final RecipeRepository recipeRepository;

    private final RecipeStepRepository recipeStepRepository;

    public SpringDependenciesConfig(SweetRepository sweetRepository, EvaluationRepository evaluationRepository,
                                    ReportRepository reportRepository, CustomerRepository customerRepository,
                                    AccountRepository accountRepository, RoleRepository roleRepository,
                                    OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
                                    TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManager, RecipeRepository recipeRepository, RecipeStepRepository recipeStepRepository)
    {
        this.sweetRepository = sweetRepository;
        this.evaluationRepository = evaluationRepository;
        this.reportRepository = reportRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.recipeRepository = recipeRepository;
        this.recipeStepRepository = recipeStepRepository;
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
    public OrderMapper orderMapper() {
        return new OrderMapper();
    }

    @Bean
    public RecipeMapper recipeMapper() {
        return new RecipeMapper();
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

    @Bean
    public OrdersReader orderReader() {
        return new OrderReaderAdapter(orderRepository, orderDetailRepository, orderMapper());
    }

    @Bean
    public OrdersWriter orderWriter() {
        return new OrderWriterAdapter(orderRepository, orderDetailRepository, orderMapper());
    }

    @Bean
    public RecipeReader recipeReader() {
        return new RecipeReaderAdapter(recipeRepository, recipeStepRepository, recipeMapper());
    }

    @Bean
    public RecipeWriter recipeWriter() {
        return new RecipeWriterAdapter(recipeRepository, recipeStepRepository, recipeMapper());
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
    public OrderService orderService() {
        return new OrderService(orderWriter(), orderReader(), sweetService(), customerService(), stripeService());
    }

    @Bean
    public RecipeService recipeService() {
        return new RecipeService(recipeReader(), recipeWriter());
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
    public OrderEndPoints orderEndPoints() {
        return new OrderEndPoints(orderService());
    }

    @Bean
    public PaymentWebhookEndpoint paymentWebhookEndpoint() {
        return new PaymentWebhookEndpoint(orderService(), stripeService());
    }

    @Bean
    public UserEndPoints userEndPoints() {
        return new UserEndPoints(customerService());
    }

    @Bean
    public AdminRecipeEndPoints adminRecipeEndPoints() {
        return new AdminRecipeEndPoints(recipeService(), minioClient());
    }

    @Bean
    public RecipeEndPoints recipeEndPoints() {
        return new RecipeEndPoints(recipeService());
    }

    // MINIO
    @Bean
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .credentials(accessKey, secretKey)
                .endpoint(minioUrl)
                .build();
    }

    // STRIPE
    @Bean
    public StripePaymentService stripeService() {
        return new StripePaymentService(stripeEndpointSecret, stripeSecretKey);
    }
}
