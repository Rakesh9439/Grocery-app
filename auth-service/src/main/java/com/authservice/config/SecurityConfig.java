package com.authservice.config;

import com.authservice.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

 private CustomUserDetailsService customUserDetailsService;
 private JwtFilter jwtFilter;




    private String[] openUrl = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/change-password",
            //  "/api/v1/welcome/message",
            //  "/api/v1/welcome/message",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**"
    };

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter) {
        this.customUserDetailsService = customUserDetailsService;

        this.jwtFilter = jwtFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // You can specify strength if needed, e.g. new BCryptPasswordEncoder(10)
    }



    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public AuthenticationProvider authProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())   // desable CSRF
                .authorizeHttpRequests(
                        req -> {
                            req.requestMatchers(openUrl).permitAll()
                                    .requestMatchers("/api/v1/welcome/message").hasRole("ADMIN")
                                    .requestMatchers("/api/v1/welcome/hello").hasAnyRole("USER","ADMIN")
                                    .anyRequest().authenticated();





                        }
                ).authenticationProvider(authProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //httpBasic();


        return http.build();
    }


}
