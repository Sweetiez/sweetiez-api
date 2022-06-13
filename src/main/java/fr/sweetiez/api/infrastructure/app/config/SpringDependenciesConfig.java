package fr.sweetiez.api.infrastructure.app.config;

import fr.sweetiez.api.adapter.delivery.authentication.AuthenticationEndPoints;
import fr.sweetiez.api.adapter.delivery.evaluation.EvaluationEndPoints;
import fr.sweetiez.api.adapter.delivery.ingredient.IngredientEndPoints;
import fr.sweetiez.api.adapter.delivery.order.OrderEndPoints;
import fr.sweetiez.api.adapter.delivery.payment.PaymentWebhookEndpoint;
import fr.sweetiez.api.adapter.delivery.report.ReportEndPoints;
import fr.sweetiez.api.adapter.delivery.sweet.AdminSweetEndPoints;
import fr.sweetiez.api.adapter.delivery.sweet.SweetEndPoints;
import fr.sweetiez.api.adapter.delivery.tray.AdminTrayEndPoints;
import fr.sweetiez.api.adapter.delivery.tray.TrayEndPoints;
import fr.sweetiez.api.adapter.delivery.user.UserEndPoints;
import fr.sweetiez.api.adapter.gateways.allergen.EdamamApi;
import fr.sweetiez.api.adapter.gateways.translator.LibreTranslateApi;
import fr.sweetiez.api.adapter.repository.accounts.AccountRepositoryAdapter;
import fr.sweetiez.api.adapter.repository.customers.CustomerReaderAdapter;
import fr.sweetiez.api.adapter.repository.customers.CustomerWriterAdapter;
import fr.sweetiez.api.adapter.repository.evaluations.EvaluationReaderAdapter;
import fr.sweetiez.api.adapter.repository.evaluations.EvaluationWriterAdapter;
import fr.sweetiez.api.adapter.repository.ingredients.IngredientRepositoryAdapter;
import fr.sweetiez.api.adapter.repository.orders.OrderReaderAdapter;
import fr.sweetiez.api.adapter.repository.orders.OrderWriterAdapter;
import fr.sweetiez.api.adapter.repository.reports.ReportRepositoryAdapter;
import fr.sweetiez.api.adapter.repository.products.sweet.SweetReaderAdapter;
import fr.sweetiez.api.adapter.repository.products.sweet.SweetWriterAdapter;
import fr.sweetiez.api.adapter.repository.products.tray.TrayReaderAdapter;
import fr.sweetiez.api.adapter.repository.products.tray.TrayWriterAdapter;
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
import fr.sweetiez.api.core.orders.ports.OrdersReader;
import fr.sweetiez.api.core.orders.ports.OrdersWriter;
import fr.sweetiez.api.core.orders.services.OrderService;
import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.Tray;
import fr.sweetiez.api.core.products.ports.ProductsReader;
import fr.sweetiez.api.core.products.ports.ProductsWriter;
import fr.sweetiez.api.core.products.services.SweetService;
import fr.sweetiez.api.core.products.services.TrayService;
import fr.sweetiez.api.core.reports.services.ReportService;
import fr.sweetiez.api.infrastructure.app.security.TokenProvider;
import fr.sweetiez.api.infrastructure.payements.StripePaymentService;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleRepository;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;
import fr.sweetiez.api.infrastructure.repository.ingredients.HealthPropertyRepository;
import fr.sweetiez.api.infrastructure.repository.ingredients.IngredientRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderDetailRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderRepository;
import fr.sweetiez.api.infrastructure.repository.reports.ReportRepository;
import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetRepository;
import fr.sweetiez.api.infrastructure.repository.products.trays.TrayRepository;
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
  
    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.secret-endpoint}")
    private String stripeEndpointSecret;

    private final SweetRepository sweetRepository;
    private final TrayRepository trayRepository;
    private final EvaluationRepository evaluationRepository;
    private final ReportRepository reportRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final IngredientRepository ingredientRepository;
    private final HealthPropertyRepository healthPropertyRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;

    public SpringDependenciesConfig(SweetRepository sweetRepository, EvaluationRepository evaluationRepository,
                                    ReportRepository reportRepository, CustomerRepository customerRepository,
                                    AccountRepository accountRepository, RoleRepository roleRepository,
                                    OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
                                    IngredientRepository ingredientRepository,
                                    HealthPropertyRepository healthPropertyRepository, TrayRepository trayRepository,
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
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.ingredientRepository = ingredientRepository;
        this.healthPropertyRepository = healthPropertyRepository;
        this.trayRepository = trayRepository;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // MAPPERS
  
    @Bean
    public EvaluationMapper evaluationMapper() {
        return new EvaluationMapper(customerMapper());
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
        return new SweetMapper(ingredientMapper(), evaluationMapper());
    }

    @Bean
    public ReportMapper reportMapper() {
        return new ReportMapper();
    }

    @Bean
    public IngredientMapper ingredientMapper() {
        return new IngredientMapper();
    }
  
    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapper();
    }

    @Bean
    public TrayMapper trayMapper() {
        return new TrayMapper(sweetMapper(), evaluationMapper());
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
    public ProductsReader<Sweet> sweetReader() {
        return new SweetReaderAdapter(sweetRepository, sweetMapper());
    }

    @Bean
    public ProductsWriter<Sweet> sweetWriter() {
        return new SweetWriterAdapter(sweetRepository, sweetMapper());
    }

    @Bean
    public ProductsReader<Tray> trayReader() {
        return new TrayReaderAdapter(trayRepository, trayMapper());
    }

    @Bean
    public ProductsWriter<Tray> trayWriter() {
        return new TrayWriterAdapter(trayRepository, trayMapper());
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
  
    @Bean
    public OrdersReader orderReader() {
        return new OrderReaderAdapter(orderRepository, orderDetailRepository, orderMapper());
    }

    @Bean
    public OrdersWriter orderWriter() {
        return new OrderWriterAdapter(orderRepository, orderDetailRepository, orderMapper());
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
        return new SweetService(sweetWriter(), sweetReader(), evaluationService(), ingredientService());
    }

    @Bean
    public TrayService trayService() {
        return new TrayService(trayWriter(), trayReader(), evaluationService(), sweetService());
    }

    @Bean
    public ReportService reportService() {
        return new ReportService(customerService(), evaluationService(), reportRepositoryPort());
    }

    @Bean
    public IngredientService ingredientService() {
        return new IngredientService(translatorGateway(), ingredientGateway(), ingredientRepositoryPort());
    }
  
    @Bean
    public OrderService orderService() {
        return new OrderService(orderWriter(), orderReader(), sweetService(), customerService(), stripeService());
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
    public TrayEndPoints trayEndPoints() {
        return new TrayEndPoints(trayService());
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
    public AdminTrayEndPoints adminTrayEndPoints() {
        return new AdminTrayEndPoints(trayService(), minioClient());
    }

    @Bean
    public ReportEndPoints reportEndPoints() {
        return new ReportEndPoints(reportService());
    }

    @Bean
    public IngredientEndPoints ingredientEndPoints() {
        return new IngredientEndPoints(ingredientService());
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
