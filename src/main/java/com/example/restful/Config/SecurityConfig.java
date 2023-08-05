package com.example.restful.Config;

import com.example.restful.Service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    protected final AccountService accountService;

    protected  final PasswordEncoder passwordEncoder;

    public SecurityConfig(AccountService accountService, PasswordEncoder passwordEncoder){
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(accountService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }
}
