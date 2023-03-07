package me.jamespurvis.loanservicewebapp.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration

public class WebSecurityConfig {


    public String[] WHITELIST = {
        "/css/*",
        "/js/*",
            "/img/*",
            "/",

    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

}
