package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.constants.EnvironmentConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@EnableWebSecurity
@Configuration
@Order(1)
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private StatelessAuthenticationFilter statelessAuthenticationFilter;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    @Autowired
    private CustomUserService userService;

//    @Value("${password.key}")
    private String passwordKey = EnvironmentConstants.PASSWORD_KEY;

    public StatelessAuthenticationSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().and().anonymous()
                .and().servletApi().and().headers().cacheControl();

        http.authorizeRequests()
                .antMatchers("/v1/users/register").permitAll()
                .antMatchers("/v1/**").authenticated()
                .antMatchers("/v1/users/*").authenticated()
                .and()
                .addFilterBefore(new JwtLoginFilter("/login", authenticationManager(),
                                tokenAuthenticationService, userService),
                        UsernamePasswordAuthenticationFilter.class)
                // add custom authentication filter for complete stateless JWT based authentication
                .addFilterBefore(statelessAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class);

    }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService)
                    .passwordEncoder(new StandardPasswordEncoder(passwordKey));

    }
}
