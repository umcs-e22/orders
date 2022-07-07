package com.assigment.securityJwt.configuration;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomTokenAuthenticationProvider customTokenAuthenticationProvider;

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/v1/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable() // No Http Basic Login
                .csrf().disable() // No CSRF token
                .formLogin().disable() // No Form Login
                .logout().disable() // No Logout
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authenticationProvider(customTokenAuthenticationProvider)
                .addFilterBefore(getFilter(), AnonymousAuthenticationFilter.class).authorizeRequests()
                .requestMatchers(getRequestMatchers()).authenticated();
    }

    private RequestMatcher getRequestMatchers() {
        return new OrRequestMatcher(new AntPathRequestMatcher("orders/"));
    }

    private Filter getFilter() throws Exception {
        return new CustomAuthenticationProcessingFilter(getRequestMatchers(), authenticationManager());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customTokenAuthenticationProvider);
    }
}
