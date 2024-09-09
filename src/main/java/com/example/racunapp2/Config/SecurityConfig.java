package com.example.racunapp2.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JsonAuthenticationSuccessHandler successHandler = new JsonAuthenticationSuccessHandler(new ObjectMapper());
        CustomUsernamePasswordAuthenticationFilter customAuthFilter = new CustomUsernamePasswordAuthenticationFilter();
        customAuthFilter.setAuthenticationManager(authenticationManager(http));
        customAuthFilter.setAuthenticationSuccessHandler(successHandler);

        return http
                .cors().and()
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/google/googleLogin", "/clients/register", "/clients/login", "/clients/check").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clients/register","/clients/check").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clients/accounts").authenticated()
                        .requestMatchers(HttpMethod.POST, "/clients/accounts").authenticated()
                        .anyRequest().authenticated() // Secure all other requests
                )
                //TODO implement oauth2
                /*.oauth2Login(oauth2 -> oauth2
                        .loginPage("/google/googleLogin") // Custom login URL
                        .defaultSuccessUrl("/accounts", true) // Redirect to /accounts on success
                        .successHandler(new SimpleUrlAuthenticationSuccessHandler("/accounts")) // Custom success handler
                )
                //    .addFilterBefore(new JwtRequestFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)

                 */
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAt(customAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtRequestFilter(), CustomUsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder()).and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
