/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Client.config;

import com.example.backend.Library.security.CorsConfig;
import com.example.backend.Library.security.auth.JwtAuthenticationFilter;
import com.example.backend.Library.security.customer.CustomerDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.backend.Admin.config.Endpoints.ADMIN_ENDPOINTS;
import static com.example.backend.Admin.config.Endpoints.PUBLIC_ADMIN_ENDPOINTS;
import static com.example.backend.Client.config.Endpoints.CLIENT_ENDPOINTS;
import static com.example.backend.Client.config.Endpoints.PUBLIC_CLIENT_ENDPOINTS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
public class ClientConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomerDetailService customerDetailService;
    private final PasswordEncoder passwordEncoder;
    private final CorsConfig corsConfig;

    public ClientConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
                               CustomerDetailService customerDetailService,
                               PasswordEncoder passwordEncoder, CorsConfig corsConfig) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customerDetailService = customerDetailService;
        this.passwordEncoder = passwordEncoder;
        this.corsConfig = corsConfig;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customerDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigSource()))
                .securityMatcher("/api/ecm/admin/**", "/api/ecm/user/**", "/api/v1/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ADMIN_ENDPOINTS).permitAll()
                        .requestMatchers(ADMIN_ENDPOINTS).hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(PUBLIC_CLIENT_ENDPOINTS).permitAll()
                        .requestMatchers(CLIENT_ENDPOINTS).hasRole("USER")
                        .anyRequest().authenticated()
                )
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                            response.getWriter().write("Unauthorized access");
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
