package teamScanner.config;

import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import teamScanner.security.jwt.JwtConfigurer;
import teamScanner.security.jwt.JwtTokenProvider;

import java.util.Arrays;


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

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(MODER_ENDPOINT).hasRole("MODER")
                .anyRequest().permitAll()
                .and()
                .cors().configurationSource(
                request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedMethods(
                            Arrays.asList(
                                    HttpMethod.GET.name(),
                                    HttpMethod.POST.name()
                            ));
                    cors.applyPermitDefaultValues();
                    return cors;
                })

                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
