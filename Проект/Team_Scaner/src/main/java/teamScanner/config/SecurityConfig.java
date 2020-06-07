package teamScanner.config;

//import io.swagger.models.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import teamScanner.security.jwt.JwtConfigurer;
import teamScanner.security.jwt.JwtTokenProvider;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String MODER_ENDPOINT = "/api/v1/moder/**";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/**";
    private static final String USER_ENDPOINT = "/api/v1/users/**";
    private static final String EVENT_ENDPOINT = "/api/v1/events/**";
    private static final String COMMENT_ENDPOINT = "/api/v1/comments/**";
    private static final String SWAGGER_API_DOCS_ENDPOINT = "/v2/**";
    private static final String SWAGGER_ENDPOINT = "/swagger-ui.html";
//https://localhost:8443/v2/api-docs

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

////
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(redirectConnector());
//        return tomcat;
//    }
//
////    @Value("${server.port.http}") //Defined in application.properties file
////            int httpPort;
//
//    @Value("${server.port}") //Defined in application.properties file
//            int httpsPort;
//
//    private Connector redirectConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(httpsPort);
//        return connector;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .requiresChannel()
//                .anyRequest()
//                .requiresSecure()
//                .and()

                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()


                .authorizeRequests()

//                .antMatchers(LOGIN_ENDPOINT).permitAll()
//                .antMatchers(USER_ENDPOINT).permitAll()
//                .antMatchers(EVENT_ENDPOINT).permitAll()
//                .antMatchers(COMMENT_ENDPOINT).permitAll()
//                .antMatchers(SWAGGER_ENDPOINT).permitAll()
//                .antMatchers(SWAGGER_API_DOCS_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(MODER_ENDPOINT).hasRole("MODER")
                .anyRequest().permitAll()
                .and()
//                .cors().configurationSource(
//                request -> {
//                    CorsConfiguration cors = new CorsConfiguration();
//                    cors.setAllowedMethods(
//                            Arrays.asList(
//                                    HttpMethod.GET.name(),
//                                    HttpMethod.POST.name()
//                            ));
//                    cors.applyPermitDefaultValues();
//                    return cors;
//                })
//
//                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
