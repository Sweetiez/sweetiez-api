package fr.sweetiez.api.infrastructure.app.config;


import fr.sweetiez.api.adapter.delivery.AdminRecipeEndPoints;
import fr.sweetiez.api.adapter.delivery.RecipeEndPoints;
import fr.sweetiez.api.adapter.delivery.RewardEndPoints;
import fr.sweetiez.api.adapter.delivery.authentication.AuthenticationEndPoints;
import fr.sweetiez.api.adapter.delivery.dashboard.DashboardEndPoints;
import fr.sweetiez.api.adapter.delivery.evaluation.EvaluationEndPoints;
import fr.sweetiez.api.adapter.delivery.event.FaceToFaceEventEndPoints;
import fr.sweetiez.api.adapter.delivery.event.StreamingEventEndPoints;
import fr.sweetiez.api.adapter.delivery.ingredient.IngredientEndPoints;
import fr.sweetiez.api.adapter.delivery.order.OrderEndPoints;
import fr.sweetiez.api.adapter.delivery.payment.PaymentWebhookEndpoint;
import fr.sweetiez.api.adapter.delivery.report.ReportEndPoints;
import fr.sweetiez.api.adapter.delivery.role.RoleEndPoints;
import fr.sweetiez.api.adapter.delivery.sweet.AdminSweetEndPoints;
import fr.sweetiez.api.adapter.delivery.sweet.SweetEndPoints;
import fr.sweetiez.api.adapter.delivery.tray.AdminTrayEndPoints;
import fr.sweetiez.api.adapter.delivery.tray.TrayEndPoints;
import fr.sweetiez.api.adapter.delivery.user.UserEndPoints;
import fr.sweetiez.api.adapter.gateways.allergen.EdamamApi;
import fr.sweetiez.api.adapter.gateways.translator.LibreTranslateApi;
import fr.sweetiez.api.adapter.repository.*;
import fr.sweetiez.api.adapter.repository.accounts.AccountRepositoryAdapter;
import fr.sweetiez.api.adapter.repository.customers.CustomerReaderAdapter;
import fr.sweetiez.api.adapter.repository.customers.CustomerWriterAdapter;
import fr.sweetiez.api.adapter.repository.dashboard.DashboardReaderAdapter;
import fr.sweetiez.api.adapter.repository.evaluations.EvaluationReaderAdapter;
import fr.sweetiez.api.adapter.repository.evaluations.EvaluationWriterAdapter;
import fr.sweetiez.api.adapter.repository.events.AnimatorsAdapter;
import fr.sweetiez.api.adapter.repository.events.face_to_face.FaceToFaceEventsAdapter;
import fr.sweetiez.api.adapter.repository.events.face_to_face.SpacesAdapter;
import fr.sweetiez.api.adapter.repository.events.streaming.StreamingEventsAdapter;
import fr.sweetiez.api.adapter.repository.ingredients.IngredientRepositoryAdapter;
import fr.sweetiez.api.adapter.repository.orders.OrderReaderAdapter;
import fr.sweetiez.api.adapter.repository.orders.OrderWriterAdapter;
import fr.sweetiez.api.adapter.repository.products.sweet.SweetReaderAdapter;
import fr.sweetiez.api.adapter.repository.products.sweet.SweetWriterAdapter;
import fr.sweetiez.api.adapter.repository.products.tray.TrayReaderAdapter;
import fr.sweetiez.api.adapter.repository.products.tray.TrayWriterAdapter;
import fr.sweetiez.api.adapter.repository.reports.ReportRepositoryAdapter;
import fr.sweetiez.api.adapter.repository.roles.RolesAdapter;
import fr.sweetiez.api.adapter.shared.*;
import fr.sweetiez.api.core.authentication.ports.AuthenticationRepository;
import fr.sweetiez.api.core.authentication.services.AuthenticationService;
import fr.sweetiez.api.core.customers.ports.CustomerReader;
import fr.sweetiez.api.core.customers.ports.CustomerWriter;
import fr.sweetiez.api.core.customers.services.CustomerService;
import fr.sweetiez.api.core.dashboard.ports.DashboardReader;
import fr.sweetiez.api.core.dashboard.services.DashboardService;
import fr.sweetiez.api.core.evaluations.ports.EvaluationReader;
import fr.sweetiez.api.core.evaluations.ports.EvaluationWriter;
import fr.sweetiez.api.core.evaluations.services.EvaluationService;
import fr.sweetiez.api.core.events.animator.Animators;
import fr.sweetiez.api.core.events.events.face_to_face_event.FaceToFaceEvents;
import fr.sweetiez.api.core.events.events.streaming_event.StreamingEvents;
import fr.sweetiez.api.core.events.space.Spaces;
import fr.sweetiez.api.core.events.use_case.face_to_face.SpaceService;
import fr.sweetiez.api.core.ingredients.ports.IngredientApi;
import fr.sweetiez.api.core.ingredients.ports.Ingredients;
import fr.sweetiez.api.core.ingredients.ports.TranslatorApi;
import fr.sweetiez.api.core.ingredients.services.IngredientService;
import fr.sweetiez.api.core.loyalty.points.ports.LoyaltyPointReader;
import fr.sweetiez.api.core.loyalty.points.ports.LoyaltyPointWriter;
import fr.sweetiez.api.core.loyalty.points.services.LoyaltyPointService;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsReader;
import fr.sweetiez.api.core.loyalty.rewards.ports.RewardsWriter;
import fr.sweetiez.api.core.loyalty.rewards.services.RewardService;
import fr.sweetiez.api.core.orders.ports.OrdersReader;
import fr.sweetiez.api.core.orders.ports.OrdersWriter;
import fr.sweetiez.api.core.orders.services.OrderService;
import fr.sweetiez.api.core.products.models.Sweet;
import fr.sweetiez.api.core.products.models.Tray;
import fr.sweetiez.api.core.products.ports.ProductsReader;
import fr.sweetiez.api.core.products.ports.ProductsWriter;
import fr.sweetiez.api.core.products.services.SweetService;
import fr.sweetiez.api.core.products.services.TrayService;
import fr.sweetiez.api.core.recipes.ports.RecipeReader;
import fr.sweetiez.api.core.recipes.ports.RecipeWriter;
import fr.sweetiez.api.core.recipes.services.RecipeService;
import fr.sweetiez.api.core.reports.services.ReportService;
import fr.sweetiez.api.core.roles.ports.Accounts;
import fr.sweetiez.api.core.roles.services.RoleService;
import fr.sweetiez.api.core.roles.ports.Roles;
import fr.sweetiez.api.infrastructure.app.security.TokenProvider;
import fr.sweetiez.api.infrastructure.notification.email.GmailSender;
import fr.sweetiez.api.infrastructure.payments.StripePaymentService;
import fr.sweetiez.api.infrastructure.repository.accounts.AccountRepository;
import fr.sweetiez.api.infrastructure.repository.accounts.RoleRepository;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerRepository;
import fr.sweetiez.api.infrastructure.repository.evaluations.EvaluationRepository;
import fr.sweetiez.api.infrastructure.repository.events.animator.AnimatorRepository;
import fr.sweetiez.api.infrastructure.repository.events.event.face_to_face.FaceToFaceEventRepository;
import fr.sweetiez.api.infrastructure.repository.events.event.streaming.StreamingEventRepository;
import fr.sweetiez.api.infrastructure.repository.events.space.ReservedSpaceRepository;
import fr.sweetiez.api.infrastructure.repository.events.space.SpaceRepository;
import fr.sweetiez.api.infrastructure.repository.ingredients.HealthPropertyRepository;
import fr.sweetiez.api.infrastructure.repository.ingredients.IngredientRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderDetailRepository;
import fr.sweetiez.api.infrastructure.repository.orders.OrderRepository;
import fr.sweetiez.api.infrastructure.repository.products.sweets.SweetRepository;
import fr.sweetiez.api.infrastructure.repository.products.trays.TrayRepository;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeRepository;
import fr.sweetiez.api.infrastructure.repository.recipe.RecipeStepRepository;
import fr.sweetiez.api.infrastructure.repository.reports.ReportRepository;
import fr.sweetiez.api.infrastructure.repository.reward.RewardRepository;
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
    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;
    private final RewardRepository rewardRepository;
    private final AnimatorRepository animatorRepository;
    private final SpaceRepository spaceRepository;
    private final ReservedSpaceRepository reservedSpaceRepository;
    private final FaceToFaceEventRepository faceToFaceEventRepository;
    private final StreamingEventRepository streamingEventRepository;

    public SpringDependenciesConfig(SweetRepository sweetRepository, TrayRepository trayRepository,
                                    EvaluationRepository evaluationRepository, ReportRepository reportRepository,
                                    CustomerRepository customerRepository, AccountRepository accountRepository,
                                    RoleRepository roleRepository, IngredientRepository ingredientRepository,
                                    HealthPropertyRepository healthPropertyRepository, OrderRepository orderRepository,
                                    OrderDetailRepository orderDetailRepository, RecipeRepository recipeRepository,
                                    RecipeStepRepository recipeStepRepository, TokenProvider tokenProvider,
                                    AuthenticationManagerBuilder authenticationManager, RewardRepository rewardRepository,
                                    AnimatorRepository animatorRepository, SpaceRepository spaceRepository,
                                    ReservedSpaceRepository reservedSpaceRepository,
                                    StreamingEventRepository streamingEventRepository,
                                    FaceToFaceEventRepository faceToFaceEventRepository)
    {
        this.sweetRepository = sweetRepository;
        this.trayRepository = trayRepository;
        this.evaluationRepository = evaluationRepository;
        this.reportRepository = reportRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.ingredientRepository = ingredientRepository;
        this.healthPropertyRepository = healthPropertyRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.recipeRepository = recipeRepository;
        this.recipeStepRepository = recipeStepRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.rewardRepository = rewardRepository;
        this.animatorRepository = animatorRepository;
        this.spaceRepository = spaceRepository;
        this.reservedSpaceRepository = reservedSpaceRepository;
        this.faceToFaceEventRepository = faceToFaceEventRepository;
        this.streamingEventRepository = streamingEventRepository;
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
    public RecipeMapper recipeMapper() {
        return new RecipeMapper();
    }

    @Bean
    public TrayMapper trayMapper() {
        return new TrayMapper(sweetMapper(), evaluationMapper());
    }

    @Bean
    public RewardMapper rewardMapper() {
        return new RewardMapper();
    }

    @Bean
    public SpaceMapper spaceMapper() {
        return new SpaceMapper();
    }

    @Bean
    public FaceToFaceEventMapper faceToFaceEventMapper() {
        return new FaceToFaceEventMapper(customerMapper());
    }

    @Bean
    public StreamingEventMapper streamingEventMapper() {
        return new StreamingEventMapper(customerMapper());
    }

    @Bean
    public RoleMapper roleMapper() {
        return new RoleMapper();
    }

    // ADAPTERS
    // REPOSITORY ADAPTERS

    @Bean
    public Animators animators() {
        return new AnimatorsAdapter(animatorRepository, customerRepository, accountRepository);
    }

    @Bean
    public Spaces spaces() {
        return new SpacesAdapter(spaceRepository, reservedSpaceRepository);
    }

    @Bean
    public FaceToFaceEvents faceToFaceEvents() {
        return new FaceToFaceEventsAdapter(faceToFaceEventRepository, animators(), spaces(), faceToFaceEventMapper());
    }

    @Bean
    public StreamingEvents streamingEvents() {
        return new StreamingEventsAdapter(streamingEventRepository, animators(), streamingEventMapper());
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

    @Bean
    public RecipeReader recipeReader() {
        return new RecipeReaderAdapter(recipeRepository, recipeStepRepository, recipeMapper());
    }

    @Bean
    public RecipeWriter recipeWriter() {
        return new RecipeWriterAdapter(recipeRepository, recipeStepRepository, recipeMapper());
    }

    @Bean
    public OrderNotifierAdapter orderNotifierAdapter() {
        return new OrderNotifierAdapter(gmailSender());
    }

    @Bean
    public RewardsWriter rewardsWriter() {
        return new RewardWriterAdapter(rewardRepository, rewardMapper());
    }

    @Bean
    public RewardsReader rewardsReader() {
        return new RewardReaderAdapter(rewardRepository, rewardMapper());
    }

    @Bean
    public LoyaltyPointWriter loyaltyPointWriter() {
        return new CustomerWriterAdapter(customerRepository, customerMapper());
    }

    @Bean
    public LoyaltyPointReader loyaltyPointReader() {
        return new CustomerReaderAdapter(customerRepository, customerMapper());
    }

    @Bean
    public DashboardReader dashboardReader() {
        return new DashboardReaderAdapter(
                sweetRepository,
                trayRepository,
                orderRepository,
                recipeRepository,
                accountRepository,
                orderMapper(),
                orderDetailRepository);
    }

    @Bean
    public Accounts accounts() {
        return new AccountRepositoryAdapter(accountRepository, roleRepository, accountMapper());
    }

    @Bean
    public Roles roles() {
        return new RolesAdapter(roleRepository, roleMapper());
    }

    @Bean
    public AccountNotifierAdapter accountNotifierAdapter() {
        return new AccountNotifierAdapter(gmailSender());
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
        return new AuthenticationService(authenticationRepository(), customerService(), tokenProvider, authenticationManager, accountNotifierAdapter());
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
        return new OrderService(
                orderWriter(),
                orderReader(),
                sweetService(),
                customerService(),
                stripeService(),
                orderNotifierAdapter(),
                trayService(),
                loyaltyPointService(),
                rewardService()
        );
    }

    @Bean
    public RecipeService recipeService() {
        return new RecipeService(recipeReader(), recipeWriter());
    }

    @Bean
    public RewardService rewardService() {
        return new RewardService(rewardsReader(), rewardsWriter(), sweetService());
    }

    @Bean
    public LoyaltyPointService loyaltyPointService() {
        return new LoyaltyPointService(loyaltyPointReader(), loyaltyPointWriter());
    }

    @Bean
    public DashboardService dashboardService() {
        return new DashboardService(dashboardReader());
    }

    @Bean
    public SpaceService spaceService() {
        return new SpaceService(spaces(), animators());
    }

    @Bean
    public RoleService roleService() {
        return new RoleService(roles(), accounts(), customerReader());
    }

    // END POINTS

    @Bean
    public RoleEndPoints roleEndPoints() {
        return new RoleEndPoints(roleService());
    }

    @Bean
    public StreamingEventEndPoints streamingEventEndPoints() {
        return new StreamingEventEndPoints(animators(), streamingEvents(), customerReader());
    }

    @Bean
    public FaceToFaceEventEndPoints faceToFaceEventEndPoints() {
        return new FaceToFaceEventEndPoints(animators(), spaces(), faceToFaceEvents(), customerReader(), spaceService());
    }
  
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
        return new IngredientEndPoints(ingredientService(), sweetService());
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

    @Bean
    public RewardEndPoints rewardEndPoints() {
        return new RewardEndPoints(rewardService());
    }

    @Bean
    public DashboardEndPoints dashboardEndPoints() {
        return new DashboardEndPoints(dashboardService());
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

    // EMAIL NOTIFIER
    @Bean
    public GmailSender gmailSender() {
        return new GmailSender();
    }

}
