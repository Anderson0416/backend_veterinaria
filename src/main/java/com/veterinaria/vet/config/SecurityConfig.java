package com.veterinaria.vet.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .exceptionHandling(exceptionHandling -> 
                exceptionHandling.authenticationEntryPoint(userAuthenticationEntryPoint)
            )
            .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authorizeRequests -> 
                authorizeRequests
                    .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/profile").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/update/{login}").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/update-password").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/upload").permitAll()
                    .requestMatchers("/uploads/**").permitAll() 
                    .anyRequest().authenticated()
            );
        return http.build(); 
    }
}
